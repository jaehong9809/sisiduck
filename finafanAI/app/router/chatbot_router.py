
from fastapi import APIRouter, HTTPException
from app.service.chatbot_service import question
from app.schemas.question import QuestionRequest
from fastapi.responses import StreamingResponse

router = APIRouter()



@router.post("/ask")
async def ask_question(request: QuestionRequest):
    answer = question(request.question)
    # answer = "aa"
    return answer