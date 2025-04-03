# app/service/redis_utils.py

import redis.asyncio as redis
import hashlib
from dotenv import load_dotenv
import os


load_dotenv()


redis_client = redis.Redis(
    host=os.getenv("REDIS_HOST"),
    port=int(os.getenv("REDIS_PORT")),
    db=1,
    password=os.getenv("REDIS_PASSWORD"),
    decode_responses=True
)

# 질문을 기반으로 캐시 키 생성 (안전하게)
def make_cache_key(text: str) -> str:
    normalized = text.strip().lower()  # ✅ 앞뒤 공백 제거 + 소문자
    hashed = hashlib.sha256(normalized.encode()).hexdigest()
    return f"chat:{hashed}"


# 캐시에서 응답 가져오기
async def get_cached_response(key: str) -> str | None:
    return await redis_client.get(key)

# 캐시에 응답 저장하기 (TTL 지정 가능)
async def set_cached_response(key: str, response: str, ttl: int = 180):
    await redis_client.set(key, response, ex=ttl)