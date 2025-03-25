
from fastapi import APIRouter
from app.service.chain import callback, classify_query
from app.service.chain import router as rout
from app.schemas.question import QuestionRequest
from fastapi.responses import StreamingResponse
import asyncio


router = APIRouter()

@router.post("")
async def ask_question(request: QuestionRequest):
    
    async def run_llm():
        await rout.ainvoke(classify_query(request.question))
        callback.finish()

    asyncio.create_task(run_llm())
    
    return StreamingResponse(callback.token_generator(), media_type="text/event-stream")