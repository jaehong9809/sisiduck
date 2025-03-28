# service/chain.py
from langchain.chains import ConversationalRetrievalChain
from langchain_community.vectorstores import Chroma
from langchain_openai import ChatOpenAI, OpenAIEmbeddings
from langchain.memory import ConversationBufferMemory
from langchain.agents import Tool, AgentExecutor, create_react_agent
from langchain_core.runnables import RunnableLambda, RunnableBranch

from app.service.search2 import fast_news_search, youtube_search, search_person_info, duckduckgo_search
from app.service.prompts2 import get_react_prompt, DUKSUNI_SYSTEM_PROMPT

# ✅ LLM & Memory
llm = ChatOpenAI(model="gpt-3.5-turbo", temperature=0.7, streaming=True)
memory = ConversationBufferMemory(memory_key="chat_history", return_messages=True)

# ✅ 도구 정의
tools = [
    Tool(name="뉴스검색", func=fast_news_search, description="최신 뉴스, 연예인 기사 등 외부 정보를 찾을 때 사용"),
    Tool(name="동영상검색", func=youtube_search, description="YouTube에서 콘서트 영상이나 방송 클립을 보고 싶을 때 사용"),
    Tool(name="인물정보검색", func=search_person_info, description="연예인의 생일, MBTI, 고향 등 기본 정보가 필요할 때 사용"),
    Tool(name="웹검색", func=duckduckgo_search, description="일반 웹 정보, 블로그, 커뮤니티 등 전체 웹 검색이 필요할 때 사용"
)
]

# ✅ 프롬프트
prompt = get_react_prompt().partial(
    system_prompt=DUKSUNI_SYSTEM_PROMPT,
    tools="\n".join([f"{t.name}: {t.description}" for t in tools]),
    tool_names=", ".join([t.name for t in tools])
)

# ✅ Agent
agent = create_react_agent(llm=llm, tools=tools, prompt=prompt)

agent_executor = AgentExecutor(
    agent=agent,
    tools=tools,
    memory=memory,
    verbose=True,
    handle_parsing_errors=False
)

# ✅ RAG 체인 (인물 정보 전용)
person_db = Chroma(persist_directory="./person_db", embedding_function=OpenAIEmbeddings())
retriever = person_db.as_retriever(search_kwargs={"k": 2})

rag_chain = ConversationalRetrievalChain.from_llm(
    llm=llm,
    retriever=retriever,
    memory=memory
)

# ✅ Agent 판단 함수
def needs_agent(input: dict) -> bool:
    question = input["input"].lower()
    # 단순 정보는 RAG
    keywords = ["생일", "나이", "키", "혈액형", "고향", "출신", "소속사", "mbti", "이름", "본명"]
    if any(k in question for k in keywords):
        return False

    # 나머지는 LLM에게 물어봄
    check_prompt = f"""
    다음 질문은 최신 뉴스, 영상, 검색 도구가 필요한가요?
    질문: "{question}"
    'YES' 또는 'NO'로만 답해주세요.
    """
    result = llm.invoke(check_prompt)
    return "YES" in result.content.upper()

# ✅ 하이브리드 체인
needs_agent_chain = RunnableLambda(needs_agent)
rag_chain_runnable = RunnableLambda(lambda x: rag_chain.invoke({"question": x["input"]}))

hybrid_chain = RunnableBranch(
    (needs_agent_chain, agent_executor),
    rag_chain_runnable
)
