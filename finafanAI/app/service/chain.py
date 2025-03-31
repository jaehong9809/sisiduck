from langchain_openai import ChatOpenAI
from langchain.memory import ConversationBufferMemory
from langchain.agents import Tool, AgentExecutor, create_react_agent
from langchain_core.runnables import RunnableLambda, RunnableBranch
from langchain_core.messages import HumanMessage, SystemMessage

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
    Tool(name="웹검색", func=duckduckgo_search, description="일반 웹 정보, 블로그, 커뮤니티 등 전체 웹 검색이 필요할 때 사용")
]

# ✅ ReAct Prompt 세팅
prompt = get_react_prompt().partial(
    system_prompt=DUKSUNI_SYSTEM_PROMPT,
    tools="\n".join([f"{t.name}: {t.description}" for t in tools]),
    tool_names=", ".join([t.name for t in tools])
)

# ✅ Agent 생성
agent = create_react_agent(llm=llm, tools=tools, prompt=prompt)
agent_executor = AgentExecutor(
    agent=agent,
    tools=tools,
    memory=memory,
    verbose=True,
    handle_parsing_errors=False
)

# ✅ 덕순이 말투 후처리
def to_friendly_tone(answer: str) -> str:
    prompt = f'다음 내용을 친구처럼 반말로 바꿔줘. 살짝 주접 섞어도 좋아~\n\n"{answer}"\n\n변환된 말투:'
    result = llm.invoke([
        SystemMessage(content=DUKSUNI_SYSTEM_PROMPT.strip()),
        HumanMessage(content=prompt)
    ])
    return result.content

# ✅ LLM이 판단: Agent가 필요한 질문인지?
def needs_agent(input: dict) -> bool:
    question = input["input"].strip()
    check_prompt = f"""
    다음 질문은 검색 도구(뉴스, 유튜브, 인물정보 등)를 사용해야 하나요?

    검색 도구가 필요한 질문이면 YES,  
    그냥 일상적인 대화면 NO만 출력해주세요.

    질문: "{question}"
    답변:
    """
    result = llm.invoke(check_prompt)
    response = result.content.strip().upper()
    print(f"[🔍 needs_agent 판단] → {response}")
    return response == "YES"

# ✅ 분기용 체인
needs_agent_chain = RunnableLambda(needs_agent)

# ✅ Agent 체인 (검색 도구 + 덕순이 말투)
agent_chain = RunnableLambda(lambda x: agent_executor.invoke(x)) | \
              RunnableLambda(lambda x: {"output": to_friendly_tone(x["output"])})

# ✅ Chat 체인 (일상 대화 → LLM 바로 호출 + 덕순이 말투)
chat_chain = RunnableLambda(lambda x: {
    "output": to_friendly_tone(
        llm.invoke([
            SystemMessage(content=DUKSUNI_SYSTEM_PROMPT.strip()),
            HumanMessage(content=x["input"])
        ]).content
    )
})

# ✅ 최종 하이브리드 체인 (Agent vs Chat)
hybrid_chain = RunnableBranch(
    (needs_agent_chain, agent_chain),
    chat_chain  # False일 때는 그냥 덕순이랑 대화~
)
