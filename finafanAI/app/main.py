from typing import Union
from fastapi import FastAPI
from .router import chatbot_router, chatbot_router2
from dotenv import load_dotenv
import os

load_dotenv()

api_key  = os.getenv("OPENAI_API_KEY")



app = FastAPI(root_path="/ai")

app.include_router(chatbot_router.router, prefix="/chatbot", tags=["Chatbot"])
app.include_router(chatbot_router2.router, prefix="/chatbot2", tags=["Chatbot2"])

@app.get("/")
def read_root():
    return {"Hello": "World"}

