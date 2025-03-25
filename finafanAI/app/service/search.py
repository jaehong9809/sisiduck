from googleapiclient.discovery import build
import feedparser
from urllib.parse import quote
import os
from langchain_openai import OpenAIEmbeddings
from langchain_community.vectorstores import Chroma

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

    print(results)
    return "\n".join(results)


embeddings = OpenAIEmbeddings()
person_db = Chroma(persist_directory="./person_db", embedding_function=embeddings)
person_retriever = person_db.as_retriever(search_kwargs={"k": 2})