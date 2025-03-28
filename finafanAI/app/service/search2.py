# service/search.py
from googleapiclient.discovery import build
import feedparser
import os
from urllib.parse import quote
from langchain_community.vectorstores import Chroma
from langchain_openai import OpenAIEmbeddings
from langchain_community.tools import DuckDuckGoSearchRun
from dotenv import load_dotenv

from duckduckgo_search import DDGS

load_dotenv()
def duckduckgo_search(query: str, max_results: int = 3) -> str:
    with DDGS() as ddgs:
        results = ddgs.text(query, max_results=max_results)
        return "\n".join([
            f"{r['title']} - {r['href']}" for r in results if 'href' in r
        ])
    
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

# YouTube 검색
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


# 인물 정보 (RAG)
embeddings = OpenAIEmbeddings()
person_db = Chroma(persist_directory="./person_db", embedding_function=embeddings)
person_retriever = person_db.as_retriever(search_kwargs={"k": 1})

def search_person_info(query: str) -> str:
    docs = person_retriever.get_relevant_documents(query)
    return "\n".join([doc.page_content for doc in docs])
