FROM python:3.10-slim

WORKDIR /app

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY . .

# 디버깅: 현재 파일 구조 보기
RUN echo "==== /app 내부 구조 ====" && ls -R /app

CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8000"]
