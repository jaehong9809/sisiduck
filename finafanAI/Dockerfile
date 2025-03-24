FROM python:3.10-slim

WORKDIR /app

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY . .

ENV FORCE_REBUILD=1
RUN echo "==== 파일 구조 ====" && ls -R /app

CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8000"]
