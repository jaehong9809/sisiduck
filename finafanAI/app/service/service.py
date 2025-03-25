import re

def classify_query(user_input: str) -> dict:
    text = user_input.lower()

    video_keywords = [
        "영상", "유튜브", "동영상", "비디오", "보다", "보여줘", "클립", "녹화", "뮤직비디오"
    ]
    news_keywords = [
        "뉴스", "기사", "소식", "보도", "속보", "헤드라인", "신문", "이슈", "날씨"
    ]
    finance_keywords = [
        "적금", "예금", "금융", "이자", "은행", "통장", "수익", "금리", "상품", "투자", "재테크"
    ]
    person_keywords = [
        "누구야", "누구", "프로필", "정보", "출연", "인물", "소개", "스케줄", "일정", "생일", "출생"
    ]

    clean_text = re.sub(r"[^가-힣\s]", "", text)

    # ✅ 우선순위: 금융 > 뉴스 > 영상 > 인물
    if any(word in clean_text for word in finance_keywords):
        return {"type": "finance", "query": user_input}

    if any(word in clean_text for word in news_keywords):
        return {"type": "news", "query": user_input}

    if any(word in clean_text for word in video_keywords):
        return {"type": "video", "query": user_input}

    if any(word in clean_text for word in person_keywords):
        
        return {"type": "person", "query": user_input}

    return {"type": "chat", "input": user_input}
