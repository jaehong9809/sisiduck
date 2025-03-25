from langchain.callbacks.base import BaseCallbackHandler
import asyncio

class SSECallbackHandler(BaseCallbackHandler):
    def __init__(self):
        self.queue = asyncio.Queue()
        self.buffer = ""

    async def on_llm_new_token(self, token: str, **kwargs):
        self.buffer += token
        # 단어로 쪼개기 (공백 포함해서)
        while " " in self.buffer:
            word, self.buffer = self.buffer.split(" ", 1)
            await self.queue.put(word + " ")  # 공백 포함해서 보내줌

    async def token_generator(self):
        while True:
            token = await self.queue.get()
            if token is None:
                break
            yield f"data: {token}\n\n"

    def finish(self):
        if self.buffer:
            self.queue.put_nowait(self.buffer)
            self.buffer = ""
        self.queue.put_nowait(None)