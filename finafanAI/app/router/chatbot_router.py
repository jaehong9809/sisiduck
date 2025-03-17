from fastapi import APIRouter, HTTPException
from app.service.chatbot_service import question

router = APIRouter()

@router.post("/ask")
async def ask_chatbot(request):
    answer = question(request)

    return {"response": answer}