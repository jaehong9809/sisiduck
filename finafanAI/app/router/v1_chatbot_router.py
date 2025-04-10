from fastapi import APIRouter
from fastapi.responses import StreamingResponse
from app.schemas.question import QuestionRequest
from app.service.v1.classify import classify_query
from app.service.v1.prompts import (
    idol_news_prompt,
    idol_video_prompt,
    default_prompt,
    person_prompt,
    app_usage_prompt,
)
from app.service.v1.search import (
    fast_news_search,
    youtube_search,
    person_retriever,
    custom_usage_retrieval,
)
from app.service.v1.callback_handler import SSECallbackHandler
from app.core.redis_utils import (
    make_cache_key,
    get_cached_response,
    set_cached_response,
)
from dotenv import load_dotenv
from langchain_openai import ChatOpenAI
from langchain_core.runnables import RunnableBranch, RunnableMap

import asyncio

load_dotenv()
router = APIRouter()


@router.post("")
async def ask_question(request: QuestionRequest):
    question = request.question
    cache_key = make_cache_key(question)

    # ✅ 1. Redis 캐시 확인
    cached = await get_cached_response(cache_key)
    if cached:

        async def stream_cached():
            for word in cached.split():
                yield f"data: {word} \n\n"
                await asyncio.sleep(0.015)  # 약간의 딜레이로 자연스러움

        return StreamingResponse(stream_cached(), media_type="text/event-stream")

    # ✅ 2. 캐시 miss → 새로 처리
    callback = SSECallbackHandler()
    collected = []  # 전체 응답 저장용

    # ✅ 콜백에 토큰 수집 기능 추가
    original_on_token = callback.on_llm_new_token

    async def capture_and_stream(token: str, **kwargs):
        collected.append(token)
        await original_on_token(token, **kwargs)

    callback.on_llm_new_token = capture_and_stream

    # ✅ LLM & 체인 세팅
    llm = ChatOpenAI(
        model="gpt-3.5-turbo", temperature=0.1, streaming=True, callbacks=[callback]
    )

    idol_news_chain = idol_news_prompt | llm
    idol_video_chain = idol_video_prompt | llm
    default_chain = default_prompt | llm

    app_usage_chain = (
        RunnableMap(
            {
                "context": lambda x: "\n\n".join(
                    doc.page_content for doc in custom_usage_retrieval(x["query"])
                ),
                "question": lambda x: x["query"],
            }
        )
        | app_usage_prompt
        | llm
    )

    person_chain = (
        RunnableMap(
            {
                "context": lambda x: "\n\n".join(
                    doc.page_content
                    for doc in person_retriever.get_relevant_documents(x["query"])
                ),
                "person": lambda x: x["query"],
                "question": lambda x: x["query"],
            }
        )
        | person_prompt
        | llm
    )

    # ✅ 분기 처리용 함수들 정의
    async def handle_video(x):
        return await idol_video_chain.ainvoke(
            {"topic": x["query"], "results": youtube_search(x["query"])}
        )

    async def handle_news(x):
        return await idol_news_chain.ainvoke(
            {"topic": x["query"], "results": fast_news_search(x["query"])}
        )

    async def handle_usage(x):
        return await app_usage_chain.ainvoke(x)

    async def handle_default(x):
        return await default_chain.ainvoke({"input": x.get("input", "")})

    async def handle_person(x):
        return await person_chain.ainvoke(x)

    # ✅ RunnableBranch 설정
    router_chain = RunnableBranch(
        (lambda x: x.get("type") == "video", handle_video),
        (lambda x: x.get("type") == "news", handle_news),
        (lambda x: x.get("type") == "usage", handle_usage),
        (lambda x: x.get("type") == "person", handle_person),  # ✅ 추가!
        handle_default,
    )

    # ✅ 체인 실행
    async def run_chain():
        try:
            input_data = await classify_query(question)
            await router_chain.with_config(
                config={"tags": ["chatbot-seq"], "run_name": "chatbot-main"}
            ).ainvoke(input_data)
        except Exception as e:
            print(f"⚠️ 오류 발생: {e}")
            await callback.queue.put("죄송합니다, 처리가 어려워요.\n")
        finally:
            callback.finish()

    # ✅ 스트리밍 응답 + 캐시 저장
    async def event_stream():
        await run_chain()

        # Redis에 전체 응답 캐싱
        full_response = "".join(collected)
        await set_cached_response(cache_key, full_response)

        async for token in callback.token_generator():
            yield token

    return StreamingResponse(event_stream(), media_type="text/event-stream")
