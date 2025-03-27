from googleapiclient.discovery import build
import feedparser
from urllib.parse import quote
import os
from langchain_openai import OpenAIEmbeddings
from langchain_community.vectorstores import Chroma

def fast_news_search(query: str) -> str:
    encoded_query = quote(query)  # 공백 및 한글 인코딩
    url = f"https://news.google.com/rss/search?q={encoded_query}&hl=ko&gl=KR&ceid=KR:ko"

    feed = feedparser.parse(url)
    results = []
    for entry in feed.entries[:3]:
        title = entry.title
        link = entry.link
        results.append(f"{title} - {link}")
    return "\n".join(results)


def youtube_search(query: str, max_results: int = 3) -> str:
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
person_db = Chroma(persist_directory="./person_db", embedding_function=embeddings)
person_retriever = person_db.as_retriever(search_kwargs={"k": 1})


# ✅ 벡터DB 로딩
embeddings = OpenAIEmbeddings()
app_db = Chroma(persist_directory="./app_usage_db", embedding_function=embeddings)
app_retriever = app_db.as_retriever(search_kwargs={"k": 3})
overview_docs = app_db.similarity_search(query="기능 안내", k=10, filter={"category": "overview"})

# 검색 쿼리에 따라 overview 문서를 우선으로 검색
def custom_usage_retrieval(query: str, top_k=3):
    from difflib import SequenceMatcher

    # 특정 키워드일 경우 overview 먼저 보여줌
    overview_keywords = ["앱 기능", "기능 알려줘", "무슨 기능", "사용법", "앱에서 뭐 해", "무엇이 가능", "쓸 수 있어", "뭐 있어"]

    if any(k in query for k in overview_keywords):
        # overview 문서만 따로 불러오기
        overview_results = [doc for doc in overview_docs]
        # 유사도 정렬 (선택 사항)
        overview_results.sort(key=lambda d: SequenceMatcher(None, query, d.page_content).ratio(), reverse=True)
        return overview_results[:top_k]

    # 일반 검색
    retriever = app_db.as_retriever(search_kwargs={"k": top_k})
    return retriever.get_relevant_documents(query)
