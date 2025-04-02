# service/search.py
from googleapiclient.discovery import build
import feedparser
import os
from urllib.parse import quote
from langchain_community.vectorstores import Chroma
from langchain_openai import OpenAIEmbeddings
from langchain_community.tools import DuckDuckGoSearchRun
from duckduckgo_search import DDGS
import warnings
from langchain_core._api.deprecation import LangChainDeprecationWarning
import requests
import re
warnings.filterwarnings("ignore", category=LangChainDeprecationWarning)

def extract_topic(text: str) -> str:
    # 가장 단순한 예: "XXX 최근 소식", "XXX 뉴스", "XXX 근황" 등의 패턴 제거
    return re.sub(r"(의\s*)?(최근\s*소식|뉴스|근황|활동)\s*(알려줘|전해줘|없어\?|좀 알려줄래)?", "", text).strip()

# 웹 검색
def duckduckgo_search(query: str, max_results: int = 3) -> str:
    with DDGS() as ddgs:
        results = ddgs.text(query, max_results=max_results)
        return "\n".join([
            f"{r['title']} - {r['href']}" for r in results if 'href' in r
        ])

# 뉴스 검색
def fast_news_search(query: str) -> str:
    query = extract_topic(query)
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


# 인물 정보 검색
embeddings = OpenAIEmbeddings()
person_db = Chroma(persist_directory="./person_db", embedding_function=embeddings)
person_retriever = person_db.as_retriever(search_kwargs={"k": 1})

def search_person_info(query: str) -> str:
    docs = person_retriever.get_relevant_documents(query)
    return "\n".join([doc.page_content for doc in docs])

def get_weather(city="Seoul"):
    url = f"https://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=c03977953046419f6c73010097b1cbfa&units=metric&lang=kr"
    response = requests.get(url)
    data = response.json()

    if response.status_code != 200:
        return "날씨 정보를 가져오지 못했습니다."

    weather = data['weather'][0]['description']                # 날씨 설명
    temp = data['main']['temp']                                # 온도
    humidity = data['main']['humidity']                        # 습도
    wind_speed = data['wind']['speed']                         # 풍속 (m/s)
    cloud_percent = data['clouds']['all']                      # 구름량 (%)

    return (
        f"대한민국 서울 현재 날씨는 '{weather}'이고, "
        f"온도는 {temp}도, 습도는 {humidity}%, "
        f"풍속은 초속 {wind_speed}m, 구름량은 {cloud_percent}%입니다."
    )