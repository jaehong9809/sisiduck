from fastapi import APIRouter, Request
from fastapi.responses import StreamingResponse
from app.schemas.question import QuestionRequest
from app.service.v2.callback_handler import SSECallbackHandler
from app.core.redis_utils import make_cache_key, get_cached_response, set_cached_response
from app.service.v2.chain import needs_agent, get_agent_chain, get_chat_chain
import asyncio
import re

router = APIRouter()

@router.post("")
async def ask_question(request: QuestionRequest, req: Request):
    question = request.question
    question = re.sub(r'\s*덕순[이가아]?\s*', ' ', question).strip()
    user_id = req.headers.get("X-User-Id", "test")
    cache_key = make_cache_key(question)

    # ✅ 1. 캐시 확인
    cached = await get_cached_response(cache_key)
    if cached:
        async def stream_cached():
            for word in cached.split():
                yield f"data: {word} \n\n"
                await asyncio.sleep(0.01)

        return StreamingResponse(stream_cached(), media_type="text/event-stream")

    # ✅ 2. 체인 실행을 위한 SSE 콜백 준비 (토큰 수집용)
    callback = SSECallbackHandler()
    collected = []

    # ✅ 콜백 오버라이딩: 토큰 수집
    original_on_token = callback.on_llm_new_token

    async def capture_and_stream(token: str, **kwargs):
        collected.append(str(token))
        await original_on_token(token, **kwargs)

    callback.on_llm_new_token = capture_and_stream

    final_output = None

    async def run_chain():
        nonlocal final_output
        try:
            is_agent = await asyncio.to_thread(needs_agent, {"input": question})
            chain = get_agent_chain(callback) if is_agent else get_chat_chain(callback)

            result = await chain.ainvoke({"input": question, "user_id": user_id})

            final_output = result["output"]
            
        except Exception as e:
            print("❌ 체인 실행 오류:", e)
            final_output = "죄송해요, 덕순이가 지금은 대답하기 어려워요 😢"
        finally:
            callback.finish()

    # ✅ 4. 스트리밍 응답 (후처리 포함)
    async def event_stream():
        await run_chain()

        await set_cached_response(cache_key, final_output)

        for word in final_output.split():
            yield f"data: {word} \n\n"
            await asyncio.sleep(0.01)

    return StreamingResponse(event_stream(), media_type="text/event-stream")
