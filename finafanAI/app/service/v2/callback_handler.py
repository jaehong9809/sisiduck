from langchain.callbacks.base import BaseCallbackHandler
import asyncio


class SSECallbackHandler(BaseCallbackHandler):
    def __init__(self):
        self.queue = asyncio.Queue()
        self.buffer = ""

    async def on_llm_new_token(self, token: str, **kwargs):
        self.buffer += token
        while " " in self.buffer:
            word, self.buffer = self.buffer.split(" ", 1)
            await self.queue.put(word + " ")

    async def token_generator(self):
        try:
            while True:
                token = await self.queue.get()
                if token is None:
                    break
                yield f"data: {token}\n\n"
        except asyncio.CancelledError:
            self.queue = asyncio.Queue()
            raise

    def finish(self):
        if self.buffer:
            self.queue.put_nowait(self.buffer)
            self.buffer = ""
        self.queue.put_nowait(None)
