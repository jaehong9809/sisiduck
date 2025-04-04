from langchain_openai import ChatOpenAI

def get_llm(streaming=False, callback=None):
    return ChatOpenAI(
        model="gpt-4-turbo",
        temperature=0.7,
        streaming=streaming,
        callbacks=[callback] if callback else None
    )

def get_hard_llm(streaming=False, callback=None):
    return ChatOpenAI(
        model="gpt-3.5-turbo",
        temperature=0.1,
        streaming=streaming,
        callbacks=[callback] if callback else None
    )

def get_soft_llm(streaming=False, callback=None):
    return ChatOpenAI(
        model="gpt-3.5-turbo",
        temperature=0.8,
        streaming=streaming,
        callbacks=[callback] if callback else None
    )