from typing import Union
from fastapi import FastAPI
from .router import v1_chatbot_router, v2_chatbot_router
from dotenv import load_dotenv
from app.core.scheduler import start_scheduler
from contextlib import asynccontextmanager
import os

load_dotenv()

@asynccontextmanager
async def lifespan(app: FastAPI):
    print("✅ FastAPI 앱 시작됨 (lifespan)")
    start_scheduler()
    yield
    print("🛑 FastAPI 앱 종료됨 (lifespan)")

app = FastAPI(root_path="/ai", lifespan=lifespan)

# ✅ 라우터 등록
app.include_router(v1_chatbot_router.router, prefix="/chatbot", tags=["ChainRouter"])
app.include_router(v2_chatbot_router.router, prefix="/chatbot2", tags=["Agent"])

@app.get("/")
def read_root():
    return {"Hello": "Ducksun"}
