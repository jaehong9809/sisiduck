import os
from langchain_openai import ChatOpenAI, OpenAIEmbeddings
from langchain_core.runnables import RunnableBranch
from langchain_core.prompts import PromptTemplate
from langchain_community.utilities.serpapi import SerpAPIWrapper
from langchain_community.vectorstores import Chroma
from dotenv import load_dotenv
from googleapiclient.discovery import build
import feedparser
from urllib.parse import quote
from langchain.callbacks.base import BaseCallbackHandler
import asyncio

load_dotenv()

# 1️⃣ OpenAI LLM 설정

llm = ChatOpenAI(model="gpt-3.5-turbo", temperature=0.5)

# 2️⃣ SerpAPI 설정

# 🔍 뉴스 검색 함수

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


# 📺 유튜브 검색 함수

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

    # 👉 필요한 정보만 정리해서 반환 (제목 + 링크)
    results = []
    for item in response.get("items", []):
        video_id = item["id"]["videoId"]
        title = item["snippet"]["title"]
        url = f"https://www.youtube.com/watch?v={video_id}"
        results.append(f"{title} - {url}")
    
    print(results)
    return "\n".join(results)

# 3️⃣ 금융 상품 검색 (Chroma)
embeddings = OpenAIEmbeddings()
vector_db = Chroma(persist_directory="finance_chroma_db", embedding_function=embeddings)
retriever = vector_db.as_retriever()

# 4️⃣ 기능별 프롬프트
idol_news_prompt = PromptTemplate.from_template(
    "다음은 '{topic}'에 대한 뉴스 검색 결과입니다. 이 내용을 요약해서 사용자에게 전달해줘:\n\n{results}"
)

idol_video_prompt = PromptTemplate.from_template(
    "다음은 '{topic}'에 대한 유튜브 영상 검색 결과입니다. 사용자에게 유용한 정보를 정리해서 알려줘:\n\n{results}"
)

finance_prompt = PromptTemplate.from_template(
    "사용자가 관심 있는 '{interest}' 관련 금융 상품을 추천해줘."
)

default_prompt = PromptTemplate.from_template(
    "너는 시니어 층에게 친절하게 일상대화를 할 수 있는 덕순이라는 캐릭터야 사용자와 친절하게 일상 대화 해줘: {input}"
)

# 5️⃣ 체인 구성
idol_news_chain = idol_news_prompt | llm
idol_video_chain = idol_video_prompt | llm
finance_chain = finance_prompt | llm
default_chain = default_prompt | llm

# 6️⃣ LCEL 기반 라우팅
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
    if "영상" in text or "유튜브" in text or "동영상" in text:
        return {"type": "video", "query": user_input}
    elif "뉴스" in text or "기사" in text or "소식" in text:
        return {"type": "news", "query": user_input}
    elif any(word in text for word in ["적금", "예금", "금융", "이자"]):
        return {"type": "finance", "interest": user_input}
    else:
        return {"type": "chat", "input": user_input}

# 실행 함수 (입력값을 정확한 형식으로 전달)
def question(query):
    print(f"🔹 입력값 확인: {query}")  # 🚀 디버깅용 출력
    keyword = classify_query(query)
    response = router.invoke(keyword)  # ✅ 수정된 입력값 형식
    return response.content