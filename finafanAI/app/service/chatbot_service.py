from langchain_openai import ChatOpenAI, OpenAIEmbeddings
from langchain_core.prompts import PromptTemplate
from langchain_core.output_parsers import StrOutputParser
from langchain_chroma import Chroma
from langchain.tools import Tool
from langchain_core.runnables import RunnableLambda
from langchain_community.utilities.serpapi import SerpAPIWrapper
from operator import itemgetter
from dotenv import load_dotenv
import os
import chromadb
from langchain.vectorstores import Chroma
from langchain.embeddings.openai import OpenAIEmbeddings
from langchain.tools import Tool
from langchain.agents import initialize_agent, AgentExecutor
from langchain.memory import ConversationBufferMemory
from langchain.chat_models import ChatOpenAI
from youtubesearchpython import VideosSearch
from langchain_community.utilities.serpapi import SerpAPIWrapper
import json

load_dotenv()

# OpenAI 모델 설정
llm = ChatOpenAI(model="gpt-3.5-turbo" , temperature=0)

# 임베딩 모델 설정
embedding_function = OpenAIEmbeddings(model="text-embedding-ada-002")

# ChromaDB 설정
chroma_client = chromadb.PersistentClient(path="./chroma_db")  # 영구 저장

# 기존 ChromaDB Collection 불러오기
collection = chroma_client.get_or_create_collection(name="my_collection")

vectorstore = Chroma(
    client=chroma_client,
    collection_name="my_collection",
    embedding_function=embedding_function
)

# 🔍 벡터 검색 함수
def search_chroma(query):
    results = vectorstore.similarity_search(query, k=3)  # 가장 유사한 3개 검색
    return "\n".join([doc.page_content for doc in results])

# 🟢 YouTube 검색 기능
def search_youtube(query):
    search = VideosSearch(query, limit=1)
    results = search.result()

    if results and 'result' in results and results['result']:
        video = results['result'][0]
        video_url = video['link']
        return f"추천 영상: {video_url}"

    return "관련 영상을 찾을 수 없습니다."

# 🟢 최신 뉴스 검색 기능
news_search_tool = SerpAPIWrapper()
# 🟢 최신 뉴스 검색 기능 (뉴스 제목 + 링크 반환)
def search_news(query):
    raw_results = news_search_tool.run(query)  # SerpAPI에서 뉴스 검색

    # 결과가 리스트인지 확인 후 처리
    if not isinstance(raw_results, list):
        return "최신 뉴스를 찾을 수 없습니다."

    return raw_results  # 🚀 Observation을 그대로 반환

# LangChain Tool 등록
tools = [
    Tool(name="YouTube Search", func=search_youtube, description="유튜브에서 관련 영상을 검색합니다."),
    Tool(name="News Search", func=search_news, description="최신 뉴스를 검색하여 제공합니다."),
    Tool(name="ChromaDB Vector Search", func=search_chroma, description="벡터 DB에서 유사한 정보를 검색합니다.")
]

# LangChain Agent 설정
memory = ConversationBufferMemory(memory_key="chat_history")

prompt = PromptTemplate(
    template="""너는 한국어를 사용하는 AI 어시스턴트야. 
    너의 역할은 사용자의 질문에 대해 한국어로 답변하는 것이야. 
    주어진 뉴스 정보를 바탕으로 한국어로 응답해줘.

    질문: {question}
    """,
    input_variables=["question"],
)

agent = initialize_agent(
    tools=tools,
    llm=llm,
    agent="zero-shot-react-description",
    verbose=True,
    memory=memory,
    prompt = prompt,
)

# 🔹 Observation을 그대로 반환하도록 설정
agent_executor = AgentExecutor(
    agent=agent,
    handle_parsing_errors=True,  # 🚀 오류 발생 시 Observation을 그대로 반환
    verbose=True
)

# 실행 함수 (입력값을 정확한 형식으로 전달)
def question(query):
    print(f"🔹 입력값 확인: {query}")  # 🚀 디버깅용 출력
    response = agent_executor.invoke({"input": query})  # ✅ 수정된 입력값 형식
    print(f"🔹 결과 확인: {response}")  # 🚀 디버깅용 출력
    return response["output"]