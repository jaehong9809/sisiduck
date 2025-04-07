from apscheduler.schedulers.asyncio import AsyncIOScheduler
from apscheduler.triggers.cron import CronTrigger

from app.core.conv_utils import redis_client


async def clear_all_memory():
    keys = await redis_client.keys("message_store:*")
    for key in keys:
        await redis_client.delete(key)
    print(f"🧹 Cleared {len(keys)} memory keys.")


def start_scheduler():
    scheduler = AsyncIOScheduler()
    scheduler.add_job(clear_all_memory, CronTrigger(minute=0))  # 매시 정각
    scheduler.start()
