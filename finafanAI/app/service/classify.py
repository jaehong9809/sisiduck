import re
from langchain.prompts import PromptTemplate
from langchain_openai import ChatOpenAI

# 1. LLM 분류기 체인 세팅
classification_prompt = PromptTemplate.from_template("""
다음 질문을 읽고, 알맞은 유형을 골라주세요.

질문: "{question}"

다음 중 하나로 정확히 대답해 주세요:
- finance (금융/적금/주식 등)
- news (뉴스/기사/인터뷰 등)
- video (유튜브/영상/뮤직비디오 등)
- person (인물/가수/프로필 등)
- chat (일상대화/잡담)

정답:
""")

llm = ChatOpenAI(temperature=0)

classification_chain = classification_prompt | llm


# 2. 키워드 기반 분류 함수
def classify_query_rule_based(user_input: str) -> str:
    text = user_input.lower()

    video_keywords = ["영상", "유튜브", "동영상", "비디오", "보다", "보여줘", "클립", "녹화", "뮤직비디오",
                      "직캠", "라이브", "풀영상", "티저", "하이라이트", "보여", "재생", "유튭"]

    news_keywords = ["뉴스", "기사", "소식", "보도", "속보", "헤드라인", "신문", "이슈", "보여줘", "최신", "근황", "인터뷰", "발표"]

    finance_keywords = ["적금", "예금", "금융", "이자", "은행", "통장", "수익", "금리", "상품", "투자", "재테크", "펀드",
                        "비과세", "이율", "환율", "주식", "자산", "정기예금", "청약"]

    person_keywords = ["누구야", "누구", "프로필", "정보", "출연", "인물", "소개", "스케줄", "일정",
                       "생일", "출생", "나이", "몇살", "이력", "경력", "학력", "활동", "배우", "가수", "알아"]

    clean_text = re.sub(r"[^가-힣a-zA-Z0-9\s]", "", text)

    if any(word in clean_text for word in finance_keywords):
        return "finance"
    if any(word in clean_text for word in news_keywords):
        return "news"
    if any(word in clean_text for word in video_keywords):
        return "video"
    if any(word in clean_text for word in person_keywords):
        return "person"
    return "chat"


# 3. 최종 하이브리드 분류기
async def classify_query(user_input: str) -> dict:
    rule_result = classify_query_rule_based(user_input)

    if rule_result != "chat":
        return {"type": rule_result, "query": user_input}

    # 룰로 못 잡은 경우 → LLM에게 분류 요청
    llm_result = await classification_chain.ainvoke({"question": user_input})
    llm_type = llm_result.content.strip().lower().split()[0]

    # 혹시 몰라서 이상한 값 방지
    if llm_type not in {"finance", "news", "video", "person", "chat"}:
        llm_type = "chat"

    if llm_type == "chat":
        return {"type": "chat", "input": user_input}
    else:
        return {"type": llm_type, "query": user_input}
