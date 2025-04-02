from typing import Union
from fastapi import FastAPI
from .router import v1_chatbot_router, v2_chatbot_router
from dotenv import load_dotenv
import os

load_dotenv()

app = FastAPI(root_path="/ai")

app.include_router(v1_chatbot_router.router, prefix="/chatbot", tags=["ChainRouter"])
app.include_router(v2_chatbot_router.router, prefix="/chatbot2", tags=["Agent"])

@app.get("/")
def read_root():
    return {"Hello": "Ducksun"}

