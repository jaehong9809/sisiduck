from langchain_openai import ChatOpenAI, OpenAIEmbeddings
from langchain_core.runnables import RunnableBranch
from langchain_community.vectorstores import Chroma
from dotenv import load_dotenv
from .prompts import idol_news_prompt, idol_video_prompt, finance_prompt, default_prompt
from .search import fast_news_search, youtube_search
from .callback_handler import SSECallbackHandler

load_dotenv()

callback = SSECallbackHandler()

llm = ChatOpenAI(model="gpt-3.5-turbo", temperature=0.5, streaming=True, callbacks=[callback])

embeddings = OpenAIEmbeddings()
# vector_db = Chroma(persist_directory="finance_chroma_db", embedding_function=embeddings)
# retriever = vector_db.as_retriever()

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
     lambda x: finance_chain.invoke({
         "topic": x["query"],
         "results": fast_news_search(x["query"])
     })),

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
        "적금", "예금", "금융", "이자", "은행", "통장", "수익", "금리", "상품", "투자", "재테크", "금리"
    ]

    # 영상 관련
    if any(word in text for word in video_keywords):
        return {"type": "video", "query": user_input}

    # 뉴스 관련
    elif any(word in text for word in news_keywords):
        return {"type": "news", "query": user_input}

    # 금융 관련
    elif any(word in text for word in finance_keywords):
        return {"type": "finance", "query": user_input}

    # 그 외 일반 대화
    else:
        return {"type": "chat", "input": user_input}
