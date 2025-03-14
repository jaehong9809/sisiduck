from typing import Union
from fastapi import FastAPI
from router import chatbot

app = FastAPI(root_path="/ai")

app.include_router(chatbot.router, prefix="/chatbot", tags=["Chatbot"])


@app.get("/")
def read_root():
    return {"Hello": "World"}

