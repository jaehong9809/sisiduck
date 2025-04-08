import asyncio
import pandas as pd
from datasets import Dataset
from difflib import SequenceMatcher
from ragas import evaluate
from ragas.metrics import (
    faithfulness,
    answer_relevancy,
    context_precision,
    context_recall,
)
from app.service.v1.prompts import idol_news_prompt
from langchain_openai import ChatOpenAI
from langchain_core.runnables import Runnable
import matplotlib.pyplot as plt
import re

# ✅ LLM & 체인 구성
llm = ChatOpenAI(temperature=0.1)
idol_news_chain: Runnable = idol_news_prompt | llm

# ✅ 덕순이 프롬프트 출력에서 요약만 추출
def clean_model_answer(raw_answer: str) -> str:
    """
    덕순이 스타일 답변에서 [LINK] 이후 제거
    """
    cleaned = re.split(r"\[LINK\]", raw_answer)[0]
    return cleaned.strip()

# ✅ 모델 응답 + 평가용 샘플 구성
async def build_ragas_sample(row):
    try:
        question = row["question"]
        contexts = row["news_titles"].split("\n")
        ground_truth = row["ground_truth"]

        input_data = {
            "topic": question,
            "results": contexts
        }

        response = await idol_news_chain.ainvoke(input_data)
        raw_answer = response.content.strip()
        answer = clean_model_answer(raw_answer)

        return {
            "question": question,
            "contexts": contexts,
            "answer": answer,
            "raw_answer": raw_answer,
            "ground_truth": ground_truth
        }

    except Exception as e:
        print(f"❌ [{row['question']}] 처리 중 오류: {e}")
        return None

# ✅ 모델 답변 vs 정답 비교 출력
def print_answer_comparisons(samples):
    print("\n📌 모델 요약 vs 기준 정답 비교\n" + "-"*50)
    for i, s in enumerate(samples):
        question = s["question"]
        answer = s["answer"]
        ground_truth = s["ground_truth"]
        similarity = SequenceMatcher(None, answer, ground_truth).ratio()

        print(f"[{i+1}] 질문: {question}")
        print(f"🔹 모델 요약:   {answer}")
        print(f"🔸 기준 정답:   {ground_truth}")
        print(f"✅ 유사도: {similarity:.2f}")
        print("-" * 60)

# ✅ 평가 루프
async def run_ragas_evaluation():
    print("🚀 Ground Truth 기반 RAG 평가 시작...\n")

    # ✅ CSV 로드
    raw_df = pd.read_excel("app/research/generated_gt_dataset.xlsx")
    print(f"📁 총 {len(raw_df)}개 질문 로드 완료")

    # ✅ 모델 답변 생성
    tasks = [build_ragas_sample(row) for _, row in raw_df.iterrows()]
    samples = await asyncio.gather(*tasks)
    samples = [s for s in samples if s]

    if not samples:
        print("⚠️ 유효한 샘플이 없습니다. 평가 종료")
        return

    # ✅ 평가용 데이터셋 변환
    df = pd.DataFrame(samples)
    ragas_dataset = Dataset.from_pandas(df)

    print(f"✅ 총 {len(df)}개 샘플 평가 준비 완료\n")

    # ✅ RAGAS 평가 실행
    results = evaluate(
        ragas_dataset,
        metrics=[
            faithfulness,
            answer_relevancy,
            context_precision,
            context_recall,
        ]
    )

    # ✅ 점수 정리
    scores_df = pd.DataFrame(results.scores)
    mean_scores = scores_df.mean().reset_index()
    mean_scores.columns = ["Metric", "Score"]

    # ✅ 시각화
# ✅ 시각화
    ax = mean_scores.plot(
        kind='bar',
        x="Metric",
        y="Score",
        legend=False,
        figsize=(8, 5),
        title="RAGAS Evaluation Metrics (Average)",
        ylim=(0, 1),
        color=["skyblue"]
    )

    # ✅ 숫자 표시
    for container in ax.containers:
        ax.bar_label(container, fmt="%.2f", label_type="edge", padding=3, fontsize=10)

    plt.ylabel("Average Score")
    plt.xticks(rotation=0)
    plt.grid(axis="y", linestyle="--")
    plt.tight_layout()
    plt.savefig("ragas_evaluation_graph.png", dpi=300)  # 저장
    plt.show()

    # ✅ 답변 비교 출력
    print_answer_comparisons(samples)

    # ✅ 결과 저장
    df.to_csv("ragas_evaluation_result_dataset.csv", index=False)
    scores_df.to_csv("ragas_evaluation_metrics.csv", index=False)
    print("\n📁 평가 결과 저장 완료!")

# ✅ 실행
if __name__ == "__main__":
    asyncio.run(run_ragas_evaluation())
