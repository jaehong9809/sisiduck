# app/service/redis_chat_memory.py

import os
from dotenv import load_dotenv
import redis.asyncio as redis

from langchain.memory.chat_message_histories import RedisChatMessageHistory
from langchain.memory import ConversationBufferMemory

# ✅ 환경 변수 로드
load_dotenv()   

# ✅ Redis 클라이언트 (메모리 초기화 용도만 사용)
redis_client = redis.Redis(
    host=os.getenv("REDIS_HOST"),
    port=int(os.getenv("REDIS_PORT")),
    db=int(os.getenv("REDIS_DB", 2)),  # memory 용 Redis DB
    password=os.getenv("REDIS_PASSWORD"),
    decode_responses=True
)

# ✅ 사용자별 LangChain 메모리 객체 생성
def get_user_memory(user_id: str) -> ConversationBufferMemory:
    """
    특정 사용자에 대한 LangChain Memory 생성 (Redis 기반)
    """
    redis_url = os.getenv("REDIS_URL", "redis://localhost:6379/1")

    history = RedisChatMessageHistory(
        session_id=user_id,
        url=redis_url
    )

    memory = ConversationBufferMemory(
        memory_key="chat_history",
        return_messages=True,
        output_key="output",
        chat_memory=history,
        k=3
    )

    return memory

# ✅ 사용자별 대화 기록 초기화 (옵션)
async def clear_user_memory(user_id: str):
    """
    특정 사용자의 대화 기록을 Redis에서 삭제
    """
    redis_key = f"message_store:{user_id}"
    await redis_client.delete(redis_key)
