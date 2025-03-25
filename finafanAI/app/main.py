from typing import Union
from fastapi import FastAPI
from .router import chatbot_router
from dotenv import load_dotenv
import os

load_dotenv()
api_key  = os.getenv("OPENAI_API_KEY")

print(f"Loaded API Key: {api_key}")
app = FastAPI(root_path="/ai")

app.include_router(chatbot_router.router, prefix="/chatbot", tags=["Chatbot"])


@app.get("/")
def read_root():
    return {"Hello": "World"}

