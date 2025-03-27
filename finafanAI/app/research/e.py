import asyncio
from app.service.search import fast_news_search
from app.service.prompts import idol_news_prompt
from langchain_openai import ChatOpenAI
from ragas import evaluate
from ragas.metrics import (
    faithfulness,
    answer_relevancy,
    context_precision,
    context_recall,
)
from difflib import SequenceMatcher
from datasets import Dataset
import pandas as pd
import matplotlib.pyplot as plt

# ✅ LLM & 체인
llm = ChatOpenAI(temperature=0.1)
idol_news_chain = idol_news_prompt | llm
sample_questions = [
    "이찬원 요즘 뭐해?",
    "임영웅 요즘 뭐해?",
    "영웅이 소식 알려줘",
    "임영웅 뉴스 찾아줘",
    "정동원 뉴스 찾아",
    "박지민 최근 기사 있어?",
    "방탄소년단 관련 뉴스 보여줘",
    "아이유 뉴스 뭐 떴어?",
    "이찬원 관련 기사 찾아줘",
    "장원영 근황 기사 알려줘",
    "지민이 무슨 뉴스 있어?",
    "정국 기사 좀 줘",
    "수지 요즘 뭐하고 있어?",
    "임영웅 기사 뭐 나왔어?",
    "이찬원 근황 알려줘",
    "아이브 뉴스 보여줘",
    "엔하이픈 관련 뉴스 있을까?",
    "김세정 최근 기사 뭐 있어?",
    "에스파 뉴스 보여줘",
    "르세라핌 기사 좀 찾아줘"
]

def print_answer_comparisons(samples):
    print("\n📌 모델 답변 vs 기준 정답 비교\n" + "-"*40)
    for i, s in enumerate(samples):
        question = s["question"]
        answer = s["answer"]
        ground_truth = s["ground_truth"]

        similarity = SequenceMatcher(None, answer, ground_truth).ratio()

        print(f"[{i+1}] 질문: {question}")
        print(f"🔹 모델 답변:      {answer}")
        print(f"🔸 기준 정답(GT): {ground_truth}")
        print(f"✅ 유사도: {similarity:.2f}")
        print("-" * 60)
# ✅ 평가용 샘플 생성 함수
async def build_ragas_sample(question: str):
    try:
        docs = fast_news_search(question) or ["(관련 기사가 없습니다.)"]
        contexts = docs[:3]

        input_data = {
            "topic": question,
            "results": docs
        }

        response = await idol_news_chain.ainvoke(input_data)
        answer = response.content.strip()

        # ✅ 자동 기준 정답 생성 (뉴스 첫 줄 기준)
        ground_truth = contexts[0].split(".")[0] if contexts else "(기준 없음)"

        return {
            "question": question,
            "contexts": contexts if isinstance(contexts, list) else [str(contexts)],
            "answer": answer,
            "ground_truth": ground_truth
        }

    except Exception as e:
        print(f"❌ [{question}] 처리 중 오류: {e}")
        return None

# ✅ 평가 루프
async def run_ragas_evaluation():
    print("🚀 뉴스 기반 체인 평가 시작...")

    # 질문 리스트는 외부에서 받거나 따로 정의된다고 가정

    samples = await asyncio.gather(*[build_ragas_sample(q) for q in sample_questions])
    samples = [s for s in samples if s]

    if not samples:
        print("⚠️ 유효한 샘플이 없습니다. 평가 종료.")
        return

    # ✅ 평가용 Dataset 생성
    df = pd.DataFrame(samples)
    ragas_dataset = Dataset.from_pandas(df)

    print(f"\n✅ 총 {len(ragas_dataset)}개 샘플 평가 준비 완료")

    # ✅ 평가 실행
    results = evaluate(
        ragas_dataset,
        metrics=[
            faithfulness,
            answer_relevancy,
            context_precision,
            context_recall,
        ]
    )

   
    import matplotlib.pyplot as plt

# 딕셔너리 형태로 변환
    scores_dict = results.scores

# 판다스로 변환
    scores_df = pd.DataFrame(scores_dict)
    mean_scores = scores_df.mean().reset_index()
    mean_scores.columns = ["Metric", "Score"]

# 시각화
    mean_scores.plot(
        kind='bar',
        x="Metric",
        y="Score",
        legend=False,
        figsize=(8, 5),
        title="RAGAS Evaluation Metrics (Average)",
        ylim=(0, 1),
        color=["skyblue"]
    )

    plt.ylabel("Average Score")
    plt.xticks(rotation=0)
    plt.grid(axis="y", linestyle="--")
    plt.tight_layout()
    plt.show()
    print_answer_comparisons(samples)
    # ✅ CSV 저장
    df.to_csv("idol_news_ragas_dataset.csv", index=False)
    scores_df.to_csv("idol_news_ragas_metrics.csv", index=False)

# ✅ 실행
if __name__ == "__main__":
    asyncio.run(run_ragas_evaluation())
