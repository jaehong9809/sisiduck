from langchain.memory import ConversationBufferMemory
from langchain.agents import Tool, AgentExecutor, create_react_agent
from langchain_core.runnables import RunnableLambda
from langchain_core.messages import HumanMessage, SystemMessage
import re
from app.service.v2.search import (
    fast_news_search,
    youtube_search,
    duckduckgo_search,
    get_weather,
    read_webpage,
    search_person_info
)
from app.service.v2.prompts import DUKSUNI_SYSTEM_PROMPT
from app.service.v2.llm import get_llm, get_hard_llm, get_soft_llm
from langchain import hub
from app.core.conv_utils import get_user_memory


# ✅ 메모리
# memory = ConversationBufferMemory(memory_key="chat_history", return_messages=True, output_key="output")

tools = [
    Tool(
        name="뉴스검색",
        func=fast_news_search,
        description="최신 뉴스, 연예인 기사, 소식 등 외부 정보를 찾을 때 사용",
    ),
    Tool(
        name="동영상검색",
        func=youtube_search,
        description="YouTube에서 영상이나 방송 클립을 보고 싶을 때 사용",
    ),
    Tool(
        name="웹검색",
        func=duckduckgo_search,
        description="연예인의 기본 정보나 경력, 나이, 데뷔 정보 등이 필요할 때 사용. 예: 커뮤니티 반응, 블로그 글, 또는 연예인 정보 검색 때 사용",
    ),
    Tool(
        name="날씨검색",
        func=get_weather,
        description="현재 날씨, 기온, 습도 등 날씨 정보가 필요할 때 사용",
    ),
    Tool(
        name="웹페이지읽기",
        func=read_webpage,
        description="링크된 웹페이지의 실제 내용을 읽고 싶을 때 사용",
    )
]



# ✅ 말투 변환
async def to_friendly_tone(answer: str) -> str:
    llm = get_soft_llm(streaming=False)
    prompt = [
        SystemMessage(content=DUKSUNI_SYSTEM_PROMPT.strip()),
        HumanMessage(
            content=f'다음 내용을 친구처럼 반말로 바꿔줘. \n\n"{answer}"\n\n변환된 말투:'
        ),
    ]
    result = await llm.ainvoke(prompt)
    return result.content.strip()


def get_user_id(x: dict) -> str:
    return x.get("user_id", "test")


# ✅ Final Answer 추출
def extract_final_answer(text: str) -> str:
    if "Final Answer:" in text:
        return text.split("Final Answer:")[-1].strip()
    return text.strip()


# ✅ Agent 판단 함수
def needs_agent(input: dict) -> bool:
    question = input["input"].strip()

    keywords = ["날씨", "온도", "습도", "추워", "더워"]
    if any(keyword in question for keyword in keywords):
        return True

    check_prompt = f"""
        다음 질문은 검색 도구(뉴스, 유튜브, 인물정보, 날씨 등)를 사용해야 하나요?

        ※ 질문에 연예인, 유명인, 유튜버, 방송인 등 인물에 대한 정보가 들어있으면 YES로 판단해주세요.

        검색 도구가 필요한 질문이면 YES,  
        그냥 일상적인 대화면 NO만 출력해주세요.

        질문: "{question}"
        답변:
        """

    result = get_hard_llm(streaming=False).invoke(check_prompt)
    response = result.content.strip().upper()

    print(f"[🔍 needs_agent 판단] → {response}")

    return response == "YES"


# ✅ Chat 체인 생성 함수 (후처리 버전)
def get_chat_chain(callback):
    async def _chat(x):
        user_id = get_user_id(x)
        memory = get_user_memory(user_id)

        llm = get_soft_llm(streaming=True, callback=callback)
        history = memory.load_memory_variables({})["chat_history"]

        result = await llm.ainvoke(
            [
                SystemMessage(content=DUKSUNI_SYSTEM_PROMPT.strip()),
                *history,
                HumanMessage(content=x["input"]),
            ]
        )

        print("\n응답 메시지 : ")
        print(result.content)

        memory.save_context({"input": x["input"]}, {"output": result.content})
        return {"output": result.content}

    return RunnableLambda(_chat)


# ✅ Agent 체인 생성 함수 (후처리 버전)
def get_agent_chain(callback):
    async def _agent(x):
        user_id = get_user_id(x)
        memory = get_user_memory(user_id)

        if "소속사" in x["input"]:
            answer = ""
            if "영웅" in x["input"]:
                answer = "임영웅의 소속사는 물고기뮤직이야!"
            elif "찬원" in x["input"]:
                answer = "이찬원의 소속사는 티엔엔터테인먼트야!"
            
            if answer:  # 👉 이름이 인식된 경우에만 처리
                friendly = await to_friendly_tone(answer)
                return {"output": friendly}

        if "생일" in x["input"]:
            answer = ""
            if "영웅" in x["input"]:
                answer = "임영웅 생일은 1991년 6월 16일이야!"
            elif "찬원" in x["input"]:
                answer = "이찬원 생일은 1996년 11월 1일이야!"

            if answer:  # 👉 이름이 인식된 경우에만 처리
                friendly = await to_friendly_tone(answer)
                return {"output": friendly}
            
        if "군대" in x["input"]:
            answer = ""
            if "영웅" in x["input"]:
                answer = "임영웅은 병역 대상이지만 아직 군 복무를 하지 않았어!"
            elif "찬원" in x["input"]:
                answer = "이찬원은 군 복무를 마치지 않았고, 미스터트롯 활동 이후 연예계 활동에 집중하고 있어!"

            if answer:
                friendly = await to_friendly_tone(answer)
                return {"output": friendly}

        if "키" in x["input"]:
            answer = ""
            if "영웅" in x["input"]:
                answer = "임영웅의 키는 약 182cm 정도야!"
            elif "찬원" in x["input"]:
                answer = "이찬원의 키는 약 176cm 정도로 알려져 있어!"

            if answer:
                friendly = await to_friendly_tone(answer)
                return {"output": friendly}

        if "가족" in x["input"] or "가족관계" in x["input"]:
            answer = ""
            if "영웅" in x["input"]:
                answer = "임영웅은 어머니와 단둘이 살며, 어머니와의 각별한 사이로 유명해!"
            elif "찬원" in x["input"]:
                answer = "이찬원은 부모님과 형제가 있는 것으로 알려져 있어. 가족과의 유대가 깊은 편이야!"

            if answer:
                friendly = await to_friendly_tone(answer)
                return {"output": friendly}

        if "mbti" in x["input"].lower():
            answer = ""
            if "영웅" in x["input"]:
                answer = "임영웅의 MBTI는 정확히 공개되진 않았지만 팬들 사이에선 INFJ나 ISFJ로 추정돼!"
            elif "찬원" in x["input"]:
                answer = "이찬원의 MBTI는 ENFP로 알려져 있어. 밝고 에너지 넘치는 성격이래!"

            if answer:
                friendly = await to_friendly_tone(answer)
                return {"output": friendly}
        
        
        llm = get_llm(streaming=True, callback=callback)

        # prompt = react_prompt_kr.partial(
        #     tools="\n".join([f"{t.name}: {t.description}" for t in tools]),
        #     tool_names=", ".join([t.name for t in tools])
        # )
        # prompt = ReActPrompt().get_prompt(tools)

        prompt2 = hub.pull("hwchase17/react-chat")

        agent = create_react_agent(llm=llm, tools=tools, prompt=prompt2)

        executor = AgentExecutor(
            agent=agent,
            tools=tools,
            memory=memory,
            handle_parsing_errors=True,
            max_iterations=10,
            max_execution_time=30,
            return_exceptions=False,
            verbose=True,
            output_key="output",
        )
        del x["user_id"]
        result = await executor.ainvoke(x)
        # intermediate_steps = result.get("intermediate_steps", [])
        # seen = set()
        # unique_tool_outputs = []
        # for step in intermediate_steps:
        #     action = step[0]  # AgentAction
        #     output = step[1]  # Tool output (observation)
        #     # Exception은 무시
        #     if action.tool == "_Exception":
        #         continue

        #     key = (action.tool, str(action.tool_input))
        #     if key not in seen:
        #         seen.add(key)
        #         unique_tool_outputs.append(output)

        # search_summary = "\n".join(unique_tool_outputs)

        final_answer = extract_final_answer(result["output"])
        if final_answer == "Agent stopped due to iteration limit or time limit.":
            final_answer = "미안 다시 물어봐줘"

        # 전체 맥락 연결
        # full_context = final_answer + "\n\n" + search_summary

        # print("\n🪄 [Final Answer 추출 결과]")
        # print(final_answer)

        friendly = await to_friendly_tone(final_answer)
        print("\n응답 메시지")
        print(friendly)

        return {"output": friendly}

    return RunnableLambda(_agent)
