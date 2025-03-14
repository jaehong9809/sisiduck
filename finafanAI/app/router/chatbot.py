from fastapi import APIRouter, HTTPException

router = APIRouter()

@router.post("/ask")
async def ask_chatbot(request):
    return {"response": "response_text"}