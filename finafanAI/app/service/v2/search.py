# service/search.py
from googleapiclient.discovery import build
import feedparser
import os
from urllib.parse import quote
from langchain_community.vectorstores import Chroma
from langchain_openai import OpenAIEmbeddings
from duckduckgo_search import DDGS
import warnings
from langchain_core._api.deprecation import LangChainDeprecationWarning
import requests
import re
from bs4 import BeautifulSoup
import requests

warnings.filterwarnings("ignore", category=LangChainDeprecationWarning)


def extract_topic(text: str) -> str:
    # 가장 단순한 예: "XXX 최근 소식", "XXX 뉴스", "XXX 근황" 등의 패턴 제거
    return re.sub(
        r"(의\s*)?(최근\s*소식|뉴스|근황|활동)\s*(알려줘|전해줘|없어\?|좀 알려줄래)?",
        "",
        text,
    ).strip()


def read_webpage(url: str) -> str:
    try:
        response = requests.get(url, timeout=5)
        soup = BeautifulSoup(response.text, "html.parser")

        # 모든 <p> 태그 텍스트 추출, 공백 제거 및 한 줄로 정리
        paragraphs = soup.find_all("p")
        cleaned_paragraphs = []

        for p in paragraphs:
            text = p.get_text().strip()
            if text:
                # 줄바꿈/중복 공백 제거 → 한 문단 단위로
                text = re.sub(r"\s+", " ", text)
                cleaned_paragraphs.append(text)

        # 문단 사이는 마침표 + 띄어쓰기 형태로 연결
        content = " ".join(cleaned_paragraphs)

        return content[:1000] if content else "본문 내용을 찾을 수 없어요."

    except Exception as e:
        return f"웹페이지 내용을 불러오지 못했어요: {str(e)}"


# 웹 검색
def duckduckgo_search(query: str, max_results: int = 5) -> str:
    with DDGS() as ddgs:
        results = ddgs.text(query, max_results=max_results)
        return "\n".join(
            [f"{r['title']} - {r['href']}" for r in results if "href" in r]
        )

def shorten_url(long_url: str) -> str:
    api_url = f"http://tinyurl.com/api-create.php?url={long_url}"
    try:
        response = requests.get(api_url, timeout=3)
        if response.status_code == 200:
            return response.text
        else:
            return long_url  # 실패하면 원래 URL 반환
    except Exception:
        return long_url

# 뉴스 검색
def fast_news_search(query: str, max_results: int = 3) -> str:
    encoded_query = quote(query)  # 공백 및 한글 인코딩
    url = f"https://news.google.com/rss/search?q={encoded_query}&hl=ko&gl=KR&ceid=KR:ko"

    feed = feedparser.parse(url)
    entries = feed.entries[:max_results]  # 👉 최대 결과 수 반영

    if not entries:
        return "관련된 뉴스 기사를 찾지 못했어ㅠㅠ"

    results = [f"{entry.title} - ({shorten_url(entry.link)})" for entry in entries]

    return "\n".join(results)


# YouTube 검색
def youtube_search(query: str, max_results: int = 3) -> str:
    api_key = os.getenv("YOUTUBE_API_KEY")

    youtube = build("youtube", "v3", developerKey=api_key)

    request = youtube.search().list(
        q=query, part="snippet", type="video", maxResults=max_results
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
person_retriever = person_db.as_retriever(search_kwargs={"k": 2})

def search_person_info(query: str) -> str:
    docs = person_retriever.get_relevant_documents(query)

    if not docs:
        return "해당 연예인에 대한 정보를 찾을 수 없습니다. 웹검색 도구를 사용해 보세요."

    # 상위 최대 3개까지, 너무 많으면 자르기
    results = []
    for doc in docs:
        content = doc.page_content.strip()
        source = doc.metadata.get("source", None)  # 없을 수도 있음
        if len(content) > 500:
            content = content[:500] + "..."
        if source:
            results.append(f"[출처: {source}]\n{content}")
        else:
            results.append(content)

    return "연예인 정보 검색 결과입니다:\n\n" + "\n\n".join(results)


def get_weather(city="Seoul"):
    url = f"https://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=c03977953046419f6c73010097b1cbfa&units=metric&lang=kr"
    response = requests.get(url)
    data = response.json()

    if response.status_code != 200:
        return "날씨 정보를 가져오지 못했습니다."

    weather = data["weather"][0]["description"]  # 날씨 설명
    temp = data["main"]["temp"]  # 온도
    humidity = data["main"]["humidity"]  # 습도
    wind_speed = data["wind"]["speed"]  # 풍속 (m/s)
    cloud_percent = data["clouds"]["all"]  # 구름량 (%)

    return (
        f"대한민국 서울 현재 날씨는 '{weather}'이고, "
        f"온도는 {temp}도, 습도는 {humidity}%, "
        f"풍속은 초속 {wind_speed}m, 구름량은 {cloud_percent}%입니다."
    )
