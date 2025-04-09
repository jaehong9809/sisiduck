# 시시덕 프로젝트 설정 및 배포 가이드

## 1. 개발 환경 및 빌드 정보

### 1.1 JVM, WAS, Python, IDE, Nginx 버전
- **JVM**: Azul Zulu 17.0.13
- **웹서버**: Spring Boot 내장 Apache Tomcat/10.1.36
- **IDE**: IntelliJ IDEA 2024.3.2
- **Python**: 3.10-slim
- **Python IDE**: Visual Studio Code
- **Nginx**: nginx/1.18.0

### 1.2 빌드 시 필요한 환경 변수
프로젝트 루트 디렉토리에 `.env` 파일을 생성하고 환경 변수들을 설정해야 합니다:

### 1.3 주요 계정 및 프로퍼티 정의 파일
- `src/main/resources/application.yml`: 기본 애플리케이션 설정
- `src/main/resources/application-local.yml`: 개발 환경 설정
- `src/main/resources/application-oauth.yml`: SSAFY에서 제공하는 OAUTH 설정 제공
- `src/main/resources/application-redis-sentinel.yml`: Redis Sentinel 설정

## 2. 외부 서비스 정보

### 2.1 금융망 API
- **제공자**: SSAFY 금융망
- **용도**: 적금 및 모임통장 기능 연동등 전반적인 핀테크 흐름시 활용

### 2.2 AWS 서비스
- **S3**: 프로필 이미지 및 연예인 이미지 저장
- **EC2**: 애플리케이션 서버 호스팅

### 2.3 OpenAI API
- **용도**: 챗봇(덕순이) 기능 제공
- **모델**: gpt-4-turbo, gpt-3.5-turbo
- **인증 방식**: API Key
- **주요 파라미터**:
    - 온도(temperature): 0.7, 0.1

## 3. 배포시 특이사항

### 3.1 Jenkins 사용

### 3.2 우분투 방화벽
- [ 1] 22                         ALLOW IN    Anywhere
- [ 2] 80                         ALLOW IN    Anywhere
- [ 3] 44                         ALLOW IN    Anywhere
- [ 4] 8989                       ALLOW IN    Anywhere
- [ 5] 8088/tcp                   ALLOW IN    Anywhere
- [ 6] 8080                       ALLOW IN    Anywhere
- [ 7] 8088                       ALLOW IN    Anywhere
- [ 8] 443                        ALLOW IN    Anywhere
- [ 9] 6379                       ALLOW IN    Anywhere
- [10] 9006                       ALLOW IN    Anywhere
- [11] 9000                       ALLOW IN    Anywhere
- [12] 3306                       ALLOW IN    Anywhere
- [13] 22 (v6)                    ALLOW IN    Anywhere (v6)
- [14] 80 (v6)                    ALLOW IN    Anywhere (v6)
- [15] 44 (v6)                    ALLOW IN    Anywhere (v6)
- [16] 8989 (v6)                  ALLOW IN    Anywhere (v6)
- [17] 8088/tcp (v6)              ALLOW IN    Anywhere (v6)
- [18] 8080 (v6)                  ALLOW IN    Anywhere (v6)
- [19] 8088 (v6)                  ALLOW IN    Anywhere (v6)
- [20] 443 (v6)                   ALLOW IN    Anywhere (v6)
- [21] 6379 (v6)                  ALLOW IN    Anywhere (v6)
- [22] 9006 (v6)                  ALLOW IN    Anywhere (v6)
- [23] 9000 (v6)                  ALLOW IN    Anywhere (v6)