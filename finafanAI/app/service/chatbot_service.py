import os
from langchain_openai import ChatOpenAI, OpenAIEmbeddings
from langchain_core.runnables import RunnableBranch
from langchain_core.prompts import PromptTemplate
from langchain_community.vectorstores import Chroma
from dotenv import load_dotenv
from googleapiclient.discovery import build
import feedparser
from urllib.parse import quote
from langchain.callbacks.base import BaseCallbackHandler
import asyncio

load_dotenv()

class SSECallbackHandler(BaseCallbackHandler):
    def __init__(self):
        self.queue = asyncio.Queue()
        self.buffer = ""

    async def on_llm_new_token(self, token: str, **kwargs):
        self.buffer += token
        # 단어로 쪼개기 (공백 포함해서)
        while " " in self.buffer:
            word, self.buffer = self.buffer.split(" ", 1)
            await self.queue.put(word + " ")  # 공백 포함해서 보내줌

    async def token_generator(self):
        while True:
            token = await self.queue.get()
            if token is None:
                break
            yield f"data: {token}\n\n"

    def finish(self):
        if self.buffer:
            self.queue.put_nowait(self.buffer)
            self.buffer = ""
        self.queue.put_nowait(None)

callback = SSECallbackHandler()

llm = ChatOpenAI(model="gpt-3.5-turbo", temperature=0.5, streaming=True, callbacks=[callback])



def fast_news_search(query: str) -> str:
    print("\n⚡ 빠른 뉴스 검색 실행 중...\n")
    encoded_query = quote(query)  # 공백 및 한글 인코딩
    
    url = f"https://news.google.com/rss/search?q={encoded_query}&hl=ko&gl=KR&ceid=KR:ko"
    
    feed = feedparser.parse(url)
    results = []
    for entry in feed.entries[:3]:
        title = entry.title
        link = entry.link
        results.append(f"{title} - {link}")
    print(results)
    return "\n".join(results)



def youtube_search(query: str, max_results: int = 3) -> str:
    print("\n🚀 유튜브 검색 중 (빠르게, 최대 개수 제한!)\n")

    api_key = os.getenv("YOUTUBE_API_KEY")
    
    youtube = build("youtube", "v3", developerKey=api_key)

    request = youtube.search().list(
        q=query,
        part="snippet",
        type="video",
        maxResults=max_results
    )
    response = request.execute()

    results = []
    
    for item in response.get("items", []):
        video_id = item["id"]["videoId"]
        title = item["snippet"]["title"]
        url = f"https://www.youtube.com/watch?v={video_id}"
        results.append(f"{title} - {url}")
    
    return "\n".join(results)

embeddings = OpenAIEmbeddings()
vector_db = Chroma(persist_directory="finance_chroma_db", embedding_function=embeddings)
retriever = vector_db.as_retriever()

idol_news_prompt = PromptTemplate.from_template(
    "너는 '덕순이'라는 캐릭터야.\n"
    "덕순이는 시니어 분들께 친절하게 정보를 전달하는 역할을 해.\n\n"
    "다음은 '{topic}'에 대한 뉴스 검색 결과야.\n"
    "이 내용을 시니어가 이해하기 쉽게 요약해서 전달해줘:\n\n"
    "{results}"
)

idol_video_prompt = PromptTemplate.from_template(
    "너는 '덕순이'라는 캐릭터야.\n"
    "덕순이는 시니어 분들께 친절하게 정보를 전달하는 역할을 해.\n\n"
    "다음은 '{topic}'에 대한 유튜브 영상 검색 결과야.\n"
    "시니어 분들에게 유용할 만한 정보를 정리해서 전달해줘:\n\n"
    "{results}"
)

finance_prompt = PromptTemplate.from_template(
    "너는 '덕순이'라는 캐릭터야.\n"
    "덕순이는 시니어 분들에게 금융 정보를 친절하고 쉽게 설명해주는 역할을 해.\n\n"
    "사용자가 관심 있는 '{interest}' 관련 금융 상품을\n"
    "시니어 눈높이에 맞게 추천해줘."
)

default_prompt = PromptTemplate.from_template(
    "너는 '덕순이'라는 캐릭터야.\n"
    "덕순이는 시니어 분들과 친절하고 따뜻하게 일상 대화를 나누는 역할을 해.\n\n"
    "다음 사용자 입력에 대해 다정하게 답해줘:\n"
    "{input}"
)


idol_news_chain = idol_news_prompt | llm
idol_video_chain = idol_video_prompt | llm
finance_chain = finance_prompt | llm
default_chain = default_prompt | llm

router = RunnableBranch(
    (lambda x: x.get("type") == "video",
     lambda x: idol_video_chain.invoke({
         "topic": x["query"],
         "results": youtube_search(x["query"])
     })),

    (lambda x: x.get("type") == "news",
     lambda x: idol_news_chain.invoke({
         "topic": x["query"],
         "results": fast_news_search(x["query"])
     })),

    (lambda x: x.get("type") == "finance",
     lambda x: finance_chain.invoke({"interest": x["interest"]})),

    lambda x: default_chain.invoke({"input": x.get("input", "")})
)

def classify_query(user_input: str) -> dict:
    text = user_input.lower()

    video_keywords = [
        "영상", "유튜브", "동영상", "비디오", "보다", "보여줘", "클립", "녹화", "뮤직비디오"
    ]

    news_keywords = [
        "뉴스", "기사", "소식", "보도", "속보", "헤드라인", "신문", "이슈"
    ]

    finance_keywords = [
        "적금", "예금", "금융", "이자", "은행", "통장", "수익", "금리", "상품", "투자", "재테크"
    ]

    # 영상 관련
    if any(word in text for word in video_keywords):
        return {"type": "video", "query": user_input}

    # 뉴스 관련
    elif any(word in text for word in news_keywords):
        return {"type": "news", "query": user_input}

    # 금융 관련
    elif any(word in text for word in finance_keywords):
        return {"type": "finance", "interest": user_input}

    # 그 외 일반 대화
    else:
        return {"type": "chat", "input": user_input}



# 실행 함수 (입력값을 정확한 형식으로 전달)
def question(query):
    print(f"🔹 입력값 확인: {query}")  # 🚀 디버깅용 출력
    
    keyword = classify_query(query)
    
    response = router.invoke(keyword)  # ✅ 수정된 입력값 형식
    
    return response.content