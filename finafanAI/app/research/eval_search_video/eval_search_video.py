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
from langchain_openai import ChatOpenAI
from langchain_core.runnables import RunnableMap
import matplotlib.pyplot as plt
import re
from app.service.v1.prompts import idol_video_prompt
from app.service.v1.search import youtube_search

# ✅ LLM + 체인
llm = ChatOpenAI(model="gpt-4", temperature=0.1)
video_chain = (
    RunnableMap({
        "topic": lambda x: x["question"],
        "results": lambda x: "\n".join(youtube_search(x["question"]))
    }) | idol_video_prompt | llm
)

# ✅ LLM 응답에서 요약만 추출
def clean_model_answer(raw_answer: str) -> str:
    return re.split(r"\[LINK\]", raw_answer)[0].strip()

# ✅ 평가용 샘플 생성
async def build_video_ragas_sample(row):
    try:
        question = row["question"]
        ground_truth = clean_model_answer(row["ground_truth"])

        # ✅ 모델이 직접 검색 & 요약
        input_data = {"question": question}
        response = await video_chain.ainvoke(input_data)

        raw_answer = response.content.strip()
        cleaned_answer = clean_model_answer(raw_answer)

        return {
            "question": question,
            "answer": cleaned_answer,
            "raw_answer": raw_answer,
            "ground_truth": ground_truth,
        }

    except Exception as e:
        print(f"❌ [{row['question']}] 처리 중 오류: {e}")
        return None

# ✅ 유사도 비교 출력 함수
def print_comparisons(samples):
    print("\n📌 모델 요약 vs 기준 정답 비교\n" + "-"*50)
    for i, s in enumerate(samples):
        sim = SequenceMatcher(None, s["answer"], s["ground_truth"]).ratio()
        print(f"[{i+1}] 질문: {s['question']}")
        print(f"🔹 모델 요약:   {s['answer']}")
        print(f"🔸 기준 정답:   {s['ground_truth']}")
        print(f"✅ 유사도: {sim:.2f}")
        print("-" * 60)
def clean_model_answer(raw_answer: str) -> str:
    """[LINK] 이전 내용만 추출 (요약만)"""
    cleaned = re.split(r"\[LINK\]", raw_answer)[0]
    return cleaned.strip()
# ✅ 전체 평가 루프
async def run_video_ragas_evaluation():
    print("🚀 유튜브 기반 요약 평가 시작...\n")

    df = pd.read_excel("app/research/video_ground_truth_dataset.xlsx")
    print(f"📁 총 {len(df)}개 질문 로드 완료")

    tasks = [build_video_ragas_sample(row) for _, row in df.iterrows()]
    samples = await asyncio.gather(*tasks)
    samples = [s for s in samples if s]

    if not samples:
        print("⚠️ 유효한 샘플이 없습니다. 평가 종료")
        return

    result_df = pd.DataFrame(samples)
    ragas_dataset = Dataset.from_pandas(result_df)

    print(f"✅ 총 {len(result_df)}개 샘플 평가 준비 완료\n")

    results = evaluate(
        ragas_dataset,
        metrics=[
            faithfulness,
            answer_relevancy,
            context_precision,
            context_recall
        ]
    )

    # ✅ 시각화
    scores_df = pd.DataFrame(results.scores)
    mean_scores = scores_df.mean().reset_index()
    mean_scores.columns = ["Metric", "Score"]

    ax = mean_scores.plot(
        kind='bar',
        x="Metric",
        y="Score",
        legend=False,
        figsize=(8, 5),
        title="📊 유튜브 영상 요약 평가 (RAGAS)",
        ylim=(0, 1),
        color=["skyblue"]
    )
    for container in ax.containers:
        ax.bar_label(container, fmt="%.2f", label_type="edge", padding=3)

    plt.ylabel("Average Score")
    plt.xticks(rotation=0)
    plt.grid(axis="y", linestyle="--")
    plt.tight_layout()
    plt.savefig("video_ragas_graph.png", dpi=300)
    plt.show()

    print_comparisons(samples)

    result_df.to_csv("video_ragas_eval_results.csv", index=False)
    scores_df.to_csv("video_ragas_metrics.csv", index=False)
    print("\n📁 평가 결과 저장 완료!")

# ✅ 실행
if __name__ == "__main__":
    asyncio.run(run_video_ragas_evaluation())
