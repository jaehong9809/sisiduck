from pydantic import BaseModel
from langchain_openai import ChatOpenAI
from langchain_core.prompts import PromptTemplate
from langchain_core.output_parsers import StrOutputParser
from langchain.vectorstores import Chroma
from langchain.embeddings import OpenAIEmbeddings
from dotenv import load_dotenv
load_dotenv()
# OpenAI 모델 설정
llm = ChatOpenAI(model="gpt-3.5-turbo")

# 임베딩 모델 설정
embedding_function = OpenAIEmbeddings(model="text-embedding-ada-002")

# ChromaDB 설정
vectorstore = Chroma(persist_directory="./chatbot_db", embedding_function=embedding_function)


retriever = vectorstore.as_retriever(search_kwargs={"k": 5})

# 질문 요청 스키마 정의
class QuestionRequest(BaseModel):
    question: str

# 프롬프트 템플릿 정의
prompt = PromptTemplate.from_template("""
당신은 금융 도우미 입니다.

### 참고 문서  
{context}

### 검사 결과  
{question}
""")


chain = (
        {"context": retriever, "question": lambda x: x}
        | prompt
        | llm
        | StrOutputParser()
)


def question(query):
    response = chain.invoke(query)
    return response
