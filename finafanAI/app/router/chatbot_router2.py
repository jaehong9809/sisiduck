# router/chatbot_router.py

from fastapi import APIRouter
from fastapi.responses import StreamingResponse
from app.schemas.question import QuestionRequest
from app.service.callback_handler import SSECallbackHandler
from app.core.redis_utils import make_cache_key, get_cached_response, set_cached_response

from app.service.chain import hybrid_chain  # 하이브리드 체인: Agent + RAG

import asyncio

router = APIRouter()

@router.post("")
async def ask_question(request: QuestionRequest):
    question = request.question
    cache_key = make_cache_key(question)

    # ✅ 1. 캐시 확인
    cached = await get_cached_response(cache_key)
    if cached:
        async def stream_cached():
            for word in cached.split():
                yield f"data: {word} \n\n"
                await asyncio.sleep(0.015)
        return StreamingResponse(stream_cached(), media_type="text/event-stream")

    # ✅ 2. SSE 콜백 + 응답 수집
    callback = SSECallbackHandler()
    collected = []

    original_on_token = callback.on_llm_new_token

    # ✅ Final Answer 이후만 사용자에게 보이게 설정
    found_final = False

    async def capture_and_stream(token: str, **kwargs):
        nonlocal found_final
        collected.append(token)

        if not found_final and "Final Answer:" in "".join(collected):
            found_final = True

        if found_final:
            await original_on_token(token, **kwargs)

    callback.on_llm_new_token = capture_and_stream

    # ✅ 체인 실행 함수
    async def run_chain():
        try:
            await hybrid_chain.ainvoke(
                {"input": question},
                config={
                    "callbacks": [callback],
                    "tags": ["덕순이"],
                    "run_name": "HybridDuksuni"
                }
            )
        except Exception as e:
            print("❌ Agent 실행 오류:", e)
            await callback.queue.put("죄송해요, 덕순이가 지금은 대답하기 어려워요 😢")
        finally:
            callback.finish()

    # ✅ 스트리밍 이벤트
    async def event_stream():
        await run_chain()

        # 캐시에 Final Answer 기준 저장
        final_answer = "".join(collected).split("Final Answer:")[-1].strip()
        await set_cached_response(cache_key, final_answer)

        async for token in callback.token_generator():
            yield token

    return StreamingResponse(event_stream(), media_type="text/event-stream")
