
from fastapi import APIRouter, HTTPException
from app.service.chatbot_service import question
from app.schemas.question import QuestionRequest
router = APIRouter()



@router.post("/ask")
async def ask_question(request: QuestionRequest):
    answer = question(request.question)

    return {"response": answer}