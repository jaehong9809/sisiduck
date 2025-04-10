import re
from langchain.prompts import PromptTemplate
from langchain_openai import ChatOpenAI
from langchain_core.runnables import RunnableMap

# ✅ 공통 LLM
llm = ChatOpenAI(temperature=0)

# ✅ 1. 분류 프롬프트 + 체인
classification_prompt = PromptTemplate.from_template(
    """
다음 질문을 읽고, 알맞은 유형을 골라주세요.

질문: "{question}"

다음 중 하나로 정확히 대답해 주세요:
- usage (앱 사용법/기능/방법/적금/통장 등)
- news (뉴스/기사/인터뷰 등)
- video (유튜브/영상/뮤직비디오 등)
- person (인물/가수/프로필 등)
- chat (일상대화/잡담)

정답:
"""
)

classification_chain = (
    RunnableMap({"question": lambda x: x["question"]}) | classification_prompt | llm
)

# ✅ 2. 쿼리 압축 프롬프트 + 체인
query_compression_prompt = PromptTemplate.from_template(
    """
다음 질문에서 핵심적인 검색어만 추출해줘. 쓸데없는 표현은 빼고 간단하게 정리해.

질문: "{question}"
검색용 압축 쿼리:
"""
)

query_compression_chain = (
    RunnableMap({"question": lambda x: x["question"]}) | query_compression_prompt | llm
)


# ✅ 3. 키워드 기반 룰 분류 함수
def classify_query_rule_based(user_input: str) -> str:
    text = user_input.lower()

    video_keywords = [
        "영상",
        "유튜브",
        "동영상",
        "비디오",
        "보다",
        "보여줘",
        "보여",
        "클립",
        "녹화",
        "재생",
        "유튭",
        "뮤직비디오",
        "직캠",
        "라이브",
        "풀영상",
        "티저",
        "하이라이트",
        "콘서트",
        "무대",
        "쇼케이스",
        "방송분",
        "팬캠",
        "유튜브영상",
        "연출",
    ]

    news_keywords = [
        "뉴스",
        "기사",
        "소식",
        "보도",
        "속보",
        "헤드라인",
        "신문",
        "이슈",
        "인터뷰",
        "발표",
        "근황",
        "요즘",
        "근래",
        "화제",
        "논란",
        "리포트",
        "브리핑",
        "언급",
        "언론",
        "인터뷰내용",
        "보도자료",
        "보도된",
        "언론보도",
    ]

    usage_keywords = [
        "앱",
        "사용법",
        "기능",
        "가입",
        "탈퇴",
        "설정",
        "알림",
        "로그인",
        "변경",
        "조회",
        "등록",
        "삭제",
        "적금",
        "예금",
        "금융",
        "이자",
        "은행",
        "통장",
        "수익",
        "상품",
        "펀드",
        "방법",
    ]

    person_keywords = [
        "누구야",
        "누구",
        "프로필",
        "정보",
        "출연",
        "인물",
        "소개",
        "스케줄",
        "일정",
        "생일",
        "출생",
        "나이",
        "몇살",
        "이력",
        "경력",
        "학력",
        "활동",
        "배우",
        "가수",
        "알아",
        "키",
        "몸무게",
        "취미",
        "특기",
        "출신",
        "데뷔",
        "경력사항",
        "이름",
        "인스타",
        "sns",
        "관련 인물",
        "팬카페",
        "소속사",
        "가족",
        "형제",
        "소속",
        "직업",
    ]

    clean_text = re.sub(r"[^가-힣a-zA-Z0-9\s]", "", text)

    if any(word in clean_text for word in usage_keywords):
        return "usage"
    if any(word in clean_text for word in news_keywords):
        return "news"
    if any(word in clean_text for word in video_keywords):
        return "video"
    if any(word in clean_text for word in person_keywords):
        return "person"
    return "chat"


# ✅ 4. 분류 + 압축 쿼리 강제 통합 함수
async def classify_query(user_input: str) -> dict:
    rule_result = classify_query_rule_based(user_input)

    if rule_result != "chat":
        # ✅ 압축 쿼리도 함께 생성

        if rule_result == "usage":
            return {"type": rule_result, "query": user_input}
        compressed = await query_compression_chain.ainvoke({"question": user_input})
        compressed = compressed.content.strip()
        # compressed = replace_nickname(compressed)
        return {"type": rule_result, "query": compressed}

    # 룰 분류 실패 → LLM 분류
    llm_result = await classification_chain.ainvoke({"question": user_input})
    llm_type = llm_result.content.strip().lower().split()[0]

    if llm_type not in {"usage", "news", "video", "person", "chat"}:
        llm_type = "chat"

    if llm_type == "chat":
        return {"type": "chat", "input": user_input}

    # ✅ chat 외 분류일 경우 → 압축 쿼리 포함
    compressed = await query_compression_chain.ainvoke({"question": user_input})
    compressed = compressed.content.strip()
    # compressed = replace_nickname(compressed)

    if llm_type == "usage":
        return {"type": rule_result, "query": user_input}

    return {"type": llm_type, "query": compressed}
