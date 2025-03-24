
from fastapi import APIRouter, HTTPException
from app.service.chatbot_service import question, callback, classify_query
from app.service.chatbot_service import router as rout
from app.schemas.question import QuestionRequest
from fastapi.responses import StreamingResponse
import asyncio
router = APIRouter()



@router.post("/ask")
async def ask_question(request: QuestionRequest):
    async def run_llm():
        await rout.ainvoke(classify_query(request.question))
        callback.finish()

    asyncio.create_task(run_llm())
    return StreamingResponse(callback.token_generator(), media_type="text/event-stream")