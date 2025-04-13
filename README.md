# A702 핀테크 특화 프로젝트

# 프로젝트명 : 시시덕

## 팀명 : [A702]
| [강진주]                           | [권민채]                                  | [김예진]                                                 | [김진영]                                                                | [이재홍]                                          | [이주호]                                        |
|---------------------------------|----------------------------------------|-------------------------------------------------------|----------------------------------------------------------------------|------------------------------------------------|----------------------------------------------|
| - 팀장<br>- 프론트엔드<br>- 앱 구조 설계 <br>- Redis 캐싱<br>- BLE 구현<br>- API 개발  | - 프론트엔드<br>- UX/UI 디자인<br>- UX/UI Lead | - 프론트엔드<br>- 기획<br>- UX/UI 디자인<br>- DB 설계<br>- API 개발 | - 백엔드<br>- DB 설계<br>- API 개발| - 백엔드<br>- AI 모델 개발<br>- 서버 아키텍처<br>- DevOps| - 백엔드<br>- 금융망 연동<br>- 연예인 적금 랭킹<br>- DevOps |

## 💁‍♂️ Detail Role
[강진주]

- 팀 일정 관리 및 프로젝트 전체 기획 총괄
- 클린 아키텍처 기반의 전체적인 앱 구조 설계
- 프론트엔드 API 통신을 위한 세팅(DI, HttpClient 환경 세팅)
- 안드로이드 STT 기술을 활용하여 AI 챗봇과의 대화 구현
- BLE 기술을 활용하여 주변 팬 탐색 기능 구현
- Redis 기반 캐싱 전략 적용 및 성능 최적화

[권민채]

- 전체 애플리케이션 UX/UI 디자인 리드
- 애플리케이션 UI 컴포넌트 설계 및 구현
- 사용자 경험 중심의 인터페이스 설계 및 구현
- 반응형 웹 컴포넌트 개발 및 스타일 가이드 작성
- 시니어 사용자를 위한 접근성 향상 기능 적용

[김예진]

- 프로젝트 초기 기획 및 요구사항 정의
- 메인 화면 UI/UX 개발 및 시니어 사용자 접근성 최적화
- 클라이언트 데이터 모델링 및 상태 관리 최적화
- 사용자 테스트 및 피드백 반영 주도

[김진영]

- 백엔드 API 설계 및 구현
- 데이터베이스 스키마 설계 및 쿼리 최적화
- 결제 및 거래 처리 시스템 개발

[이재홍]

- LangChain Agent 기반 AI 챗봇 개발
- 백엔드 서버 아키텍처 설계 및 구현
- 클라우드 인프라 관리 및 최적화

[이주호]

- 금융망 연동 및 통합 
- 연예인 적금 랭킹 시스템 설계 및 구현
- CI/CD 파이프라인 구축 및 배포 자동화

## Languages
![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=java&logoColor=white)
![Python](https://img.shields.io/badge/Python-3776AB?style=flat-square&logo=python&logoColor=white)

## Technologies
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat-square&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=flat-square&logo=redis&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=flat-square&logo=git&logoColor=white)
![GitLab](https://img.shields.io/badge/GitLab-FCA121?style=flat-square&logo=gitlab&logoColor=white)
![Jira](https://img.shields.io/badge/Jira-0052CC?style=flat-square&logo=jira&logoColor=white)
![AWS](https://img.shields.io/badge/AWS-232F3E?style=flat-square&logo=amazonaws&logoColor=white)
![Jenkins](https://img.shields.io/badge/Jenkins-D24939?style=flat-square&logo=jenkins&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-59666C?style=flat-square&logo=hibernate&logoColor=white)
![QueryDSL](https://img.shields.io/badge/QueryDSL-007ACC?style=flat-square&logo=java&logoColor=white)
![WebSocket](https://img.shields.io/badge/WebSocket-010101?style=flat-square&logo=socketdotio&logoColor=white)
![LangChain](https://img.shields.io/badge/LangChain-3178C6?style=flat-square&logo=chainlink&logoColor=white)
![ChatGPT](https://img.shields.io/badge/chatGPT-74aa9c?style=for-the-badge&logo=openai&logoColor=white)
## 📝 목차
- [개요](#-개요)
- [Pain Point](#-pain-point)
- [Idea / Solution](#-idea--solution)
- [아키텍처](#-아키텍처)
- [결과물](#-결과물)
- [협업 방식](#-협업-방식)
- [팀의 개발 문화](#-팀의-개발-문화)
- [ERD](#-erd)
- [테스트 및 모니터링](#-테스트-및-모니터링)
- [왜 이 기술을 사용했는가?](#-왜-이-기술을-사용했는가)
- [리팩토링 & 성능 개선](#-리팩토링--성능-개선)
- [성과 및 회고](#-성과-및-회고)

## 📝 개요
시시덕은 시니어 금융 기반 덕질 플랫폼으로, 연예인 적금과 모금 통장을 제공하고 AI 비서(덕순이)를 통해 연예인과 연관 대화나 일상 대화를 수행하는 서비스입니다. 기존 금융 서비스에 엔터테인먼트 요소와 팬덤 문화를 결합하여 새로운 형태의 금융 서비스와 팬 활동 문화를 형성하고자 합니다. 특히 시니어 사용자에(5060 세대) 특화된 UX/UI를 제공하여 디지털 금융 접근성을 높이고, 금융 앱에 대한 심리적 허들을 낮춥니다.

## 🧐 Pain Point
1. **기존 은행 앱의 한계**: 기존 은행 애플리케이션은 신뢰성과 보수성이 강해 연예인 적금과 같은 창의적인 시도가 부족합니다.
2. **팬 활동의 경제적 가치화 부재**: 팬들이 좋아하는 연예인을 경제적으로 지원하고 이를 통해 소속감을 느낄 수 있는 공식적인 채널이 부족합니다.
3. **시니어 사용자의 디지털 금융 접근성**: 복잡한 UX/UI로 인해 시니어 사용자들이 디지털 금융 서비스 이용에 어려움을 겪고 있습니다.
4. **팬 커뮤니티 형성의 어려움**: 같은 연예인을 좋아하는 팬들이 오프라인에서 만나고 교류할 수 있는 기회가 제한적입니다.

## 💡 Idea / Solution
### 연예인 적금 시스템
- 좋아하는 연예인을 선택하여 적금 가입
- 응원 메시지와 함께 자율 적금 입금 가능
- 일/주/월별 랭킹을 통한 팬 활동 게임화

### 모임 통장 & 모금 기능
- 특정 목적(이벤트, 생일 등)을 위한 모금 통장 개설
- 목표 금액 설정 및 달성률 시각화
- 모임장 관리 및 중도 해지 로직 구현

### 주변 동료 탐색 기능
- BLE 기술을 활용하여 주변에 같은 연예인 적금에 가입한 팬 탐색
- UUID 변환을 통한 보안 강화
- 오프라인 팬 커뮤니티 형성 지원

### 덕순이 AI 챗봇
- LangChain Agent 기반의 공감형 AI 대화 시스템
- 연예인 정보, 뉴스, 영상 등 맞춤형 정보 제공
- 사용자의 질문을 이해하고 공감하는 대화 능력

### 차별화 포인트
- 금융과 엔터테인먼트의 결합으로 새로운 시장 창출
- 시니어 사용자를 위한 특화된 UX/UI 디자인
- 온/오프라인을 연결하는 팬 커뮤니티 형성
- 실시간 랭킹 및 모금 현황을 통한 참여 동기 부여

## 📈 아키텍처
![img.png](readmeimg/architecture.png)

### 시스템 구성
- **프론트엔드**: Kotlin
- **백엔드**: Spring Boot (RESTful API, WebSocket)
- **데이터베이스**: MySQL (주 데이터 저장), Redis (캐싱, 랭킹)
- **인프라**: AWS EC2, S3
- **CI/CD**: Jenkins, Docker, Gitlab Webhook
- **AI**: gpt-4, LangChain Agent

### 핵심 컴포넌트
- **RankingService**: 연예인 및 팬 랭킹 계산 로직
- **RankingWebSocketService**: 실시간 랭킹 업데이트
- **SavingsService**: 결제 및 적금 처리
- **FundingService**: 모임 통장 및 모금 관리
- **BLEService**: 주변 팬 탐색 기능
- **ChatbotService**: LangChain 기반 AI 대화 시스템

### 데이터 흐름
1. 사용자 요청 → Nginx(RoundRobin) -> SpringBoot
2. 연예인 적금 입금 → RankingService 랭킹 재계산 → WebSocket으로 실시간 전송
3. 모금 통장 생성/참여 → FundingService → 목표 달성 시 알림
4. 주변 동료 탐색 요청 → BLEService → UUID 변환 및 매칭
5. 챗봇 질문 요청 → 분기 LLM → Agent or Chat → 결과 반환

### 보안 구조
- JWT 기반 인증/인가
- 민감 정보 노출 최소화
- BLE 통신 시 UUID 변환으로 기기 보안 강화
- 결제 정보 암호화 및 토큰화

## 🎁 결과물
### 메인 화면
![메인 화면](readmeimg/main.png)
- 메인 페이지로서 연예인 적금 및 여러 기능의 게이트웨이
- 시니어 UX 향상을 위한 확대된 폰트 및 직관적인 아이콘 배치
- 사용자 맞춤형 추천 및 최신 정보 표시

### 모금 통장
![모금 통장](readmeimg/funding.png)
- 연예인 모금 통장 개설 및 목표 금액 설정 기능
- 모금 진행 상황 시각화 및 참여자 목록 제공
- 모임장 교체 및 중도 해지 로직 구현

### 연예인 적금
![연예인 적금](readmeimg/entersaving.png)
- 연예인별 적금 상품 선택 및 가입
- 이자율 및 만기/중도해지
- 일/주/전체 기간 랭킹 실시간 표시
- 계좌 연결 및 인증

### 주변 팬 탐색 기능
![주변 팬 탐색](readmeimg/aroundfan.png)
- BLE를 활용한 주변 동료 탐색
- Advertise 기능을 통해 UUID 패킷을 뿌리고, Scanner를 통해 UUID를 감지하는 방식
- Random UUID와 TTL 설정을 통해 보안 강화
- 같은 연예인 적금 가입자 발견 시 알림 및 프로필 표시


### AI 챗봇
<img src="readmeimg/chatbot.jpg" alt="img" width="200"/>

- LLM을 활용한 유연한 대화 분기 처리
- Agent를 사용해 질문 유형에 따라 최적의 정보 소스 선택
- 연예인 정보, 뉴스, 영상 등 다양한 컨텐츠 제공
- 공감과 정보 전달을 균형있게 제공하는 대화 모델

## ⏰ 협업 방식
![img.png](readmeimg/collaboration.png)
- Notion, GitLab Issue, Jira를 모두 사용하여 팀원의 작업 상황과 논의 사항 빠르게 공유

### 일정 관리
![번다운 차트](readmeimg/burndown.png)
- Jira를 활용한 스프린트 기반 애자일 개발
- 2주 단위 스프린트로 MVP 및 추가 기능 개발 관리
- 일일 스탠드업 미팅으로 진행 상황 공유 및 블로커 해결

### 코드 리뷰 프로세스
![코드 리뷰](readmeimg/codereview.png)
- GitLab MR(Merge Request)을 통한 코드 리뷰
- 최소 1명 이상의 승인 필요
- 코드 컨벤션 준수 및 리뷰 필수
- 코드 리뷰를 통해 코드 개선 방향, 발전 방향 제시


### 문서화
![img.png](readmeimg/jiradash.png)
- Confluence를 활용한 기술 문서 및 회의록 관리
- API 문서 자동화 (Swagger)
- 아키텍처 및 플로우 다이어그램 작성

## 🎈 팀의 개발 문화
### 핵심 원칙
- 필요한 내용은 바로 직접 소통을 통해 해결하고, 전달이 어려운 정보는 반드시 문서화하여 공유한다.
- 모든 결정사항과 회의록은 문서화하여 팀원 사이 Align 유지한다.
- 문제 발생 시 혼자 해결하는 것이 아니라 팀에 빠르게 상황을 공유하고 함께 해결책을 모색한다.
- 개인의 성장과 팀의 성장을 동시에 추구하여, 다양한 기술 정보나 기회를 공유한다.

### 소통 방식
- 매일 아침, 15분 스탠드업 미팅
- 주 1회, KPT 회고를 통해 개선점 발굴
- 기술적 어려움 발생 시 페어 프로그래밍 활용


## 📈 ERD
![ERD](readmeimg/erd.png)

### 주요 엔티티
- **User**: 사용자 정보 및 인증 관련 데이터
- **Entertainer**: 연예인 정보 및 메타데이터
- **Saving**: 연예인 적금 상품 및 가입 정보
- **SavingTransaction**: 적금 입출금 내역
- **Funding**: 모금 통장 정보 및 상태
- **FundingParticipant**: 모금 참여자 및 금액 정보
- **Ranking**: 일/주/전체 랭킹 정보
- **Message**: 응원 메시지 및 댓글

### 주요 관계
- User-Saving: 1:N (한 사용자가 여러 적금 가입 가능)
- Entertainer-Saving: 1:N (한 연예인에 여러 적금 상품 가능)
- User-Funding: N:M (FundingParticipant를 통해 구현)
- Saving-SavingTransaction: 1:N (한 적금에 여러 거래 내역)

## 🔨 모니터링

### 성능 테스트
![성능 테스트](readmeimg/performance.png)
- JMeter를 활용한 부하 테스트
- 동시 사용자 1,000명 기준 응답 시간 < 500ms 목표
- 랭킹 시스템 실시간 업데이트 성능 검증

## 💎 왜 이 기술을 사용했는가?
### Redis Sentinel
- **도입 배경**: 랭킹 시스템의 고가용성과 실시간 데이터 처리 필요
- **대안과의 비교**:
    - Redis Cluster: 수평 확장성은 좋으나 구성 복잡
    - 단일 Redis: 구성 간단하나 SPOF(Single Point of Failure) 문제
    - Redis Sentinel: 고가용성 확보와 구성 복잡도 간 최적의 균형
- **실제 적용 결과**:
    - 마스터 노드 장애 시 자동 장애 조치로 서비스 중단 최소화
    - ZSet을 활용한 효율적인 랭킹 계산 및 조회

### LangChain Agent
- **도입 배경**: 유연하고 확장 가능한 AI 챗봇 시스템 필요
- **대안과의 비교**:
    - 단순 LLM 방식: 구현 간단하나 기능 확장 어려움
    - 단순 RAG 방식: 최신 정보 반영을 위한 작업 필요
- **실제 적용 결과**:
    - 질문 유형을 분석하여 일반 채팅과 질문을 분류하여 처리 속도 향상
    - 다양한 툴로 정보 소스 활용 가능 (뉴스, 영상, 웹 정보 등)
    - 새로운 기능 추가가 용이한 구조
- **성능 비교**:
    * answer_correctness(간단한 질문)
        - 기존 모델: 0.42
        - Agent 모델: 0.43
    * answer_correctness(어려운 질문)
        - 기존 모델: 0.43
        - Agent 모델: 0.60

## 🚀 리팩토링 & 성능 개선

### BLE 통신 최적화
![BLE 최적화](readmeimg/ble_improvement.png)
- **문제점**:
- **개선 방법**:
- **개선 결과**:

## 📌 성과 및 회고
### 프로젝트 성과
- **기술적 성과**:
    - Redis Sentinel을 활용한 고가용성 랭킹 시스템 구축
    - LangChain Agent 기반 AI 챗봇 시스템 개발
    - BLE 기술을 활용한 주변 팬 탐색 기능 구현

- **비즈니스 성과**:
    - 핀테크 특화 프로젝트 우수상 수상
    - 시니어 금융과 엔터테인먼트의 결합이라는 새로운 비즈니스 모델 제시
    - 팬 활동의 경제적 가치화 가능성 증명

### 배운 점
- **기술적 측면**:
    - 대용량 데이터 처리 및 캐싱 전략
    - 실시간 시스템 구축 및 최적화 방법
    - AI 기술과 기존 서비스의 효과적인 통합
    - 모바일 기기 하드웨어(BLE) 활용 경험

- **협업 측면**:
    - 애자일 방법론의 실제 적용 및 개선
    - 효과적인 기술 문서화 및 지식 공유 방법
    - 다양한 백그라운드를 가진 팀원 간 소통 개선

### 아쉬운 점 & 개선 방향
- **한계점**:
    - 챗봇 모델의 응답 속도 개선 필요
    - 모바일 기기별 BLE 구현 차이로 인한 호환성 문제
    - 대규모 사용자 테스트 부족

- **추후 개선 방향**:
    - 오프라인 이벤트 연계 기능 강화
    - 모임 통장 기능 확장 (목표 달성 시 자동 기부 등)
    - 다양한 연예인 계약 및 제휴 모델 개발

### 시연 영상
[시시덕 시연 영상 링크](https://youtu.be/example)

