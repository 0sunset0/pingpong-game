## 🏓 소개

탁구 게임 서비스 백엔드 서버입니다.
유저가 방을 생성하고, 참가하고, 팀을 구성한 후 게임을 진행할 수 있습니다.

---

## 🚀 기술

- **Backend:** Java 17, Spring Boot 3.3.6, Spring Data JPA, QueryDSL
- **Database:** H2 (인메모리 DB)
- **API 문서화:** Swagger (SpringDoc OpenAPI 3)

---

## ✨ 주요 기능

- **게임 종료 이벤트 비동기 처리** 
    - `ApplicationEventPublisher`를 사용해 **게임 종료 이벤트를 등록**하고, `@EventListener`와 `TaskScheduler`를 활용하여 이벤트 비동기 실행
- **`QueryDSL`을 활용한 페이징 API 구현**
- **커스텀 예외 정의 및 전역 예외 처리**
- **Swagger API 문서화 (`SpringDoc OpenAPI 3`)**

---

## 📖 API 설명

| HTTP Method | Endpoint                   | 설명                               |
|-------------|----------------------------|----------------------------------|
| `GET`       | `/health`                  | **헬스 체크 API** - 서버 상태 확인         |
| `GET`       | `/user`                    | **유저 전체 조회 API** - 모든 유저 목록 조회   |
| `POST`      | `/init`                    | **초기화 API** - 시스템 초기화 수행         |
| `GET`       | `/room`                    | **방 전체 조회 API** - 생성된 방 목록 조회    |
| `GET`       | `/room/{roomId}`           | **방 상세 조회 API** - 특정 방 정보 조회     |
| `POST`      | `/room`                    | **방 생성 API** - 새로운 방 생성          |
| `POST`      | `/room/attention/{roomId}` | **방 참가 API** - 특정 방에 참가          |
| `POST`      | `/room/out/{roomId}`       | **방 나가기 API** - 현재 참여 중인 방에서 나가기 |
| `PUT`       | `/room/start/{roomId}`     | **게임 시작 API** - 특정 방에서 게임 시작     |
| `PUT`       | `/team/{roomId}`           | **팀 변경 API** - 특정 방에서 팀을 변경      |

---

## 🗄 ERD

![image](https://github.com/user-attachments/assets/76dcb631-493b-40e3-8b85-3922b2e69af8)


---

## 코드 스타일, 브랜치 전략, 커밋 컨벤션

- 코드 스타일 : [캠퍼스 핵데이 Java 코딩 컨벤션](https://naver.github.io/hackday-conventions-java/)
- 브랜치 전략 : git branch
- 커밋 컨벤션 : 태그: 제목 (#이슈번호)
    
    
    | 태그 종류 | 의미 |
    | --- | --- |
    | `feat` | 새로운 기능 추가 |
    | `fix` | 버그 수정 |
    | `docs` | 문서 수정 |
    | `style` | 코드 스타일 수정 (코드의 동작에 영향 없음) |
    | `refactor` | 리팩토링 (기능 변경 없이 코드 구조 변경) |
    | `perf` | 성능 개선 |
    | `test` | 테스트 코드 추가/수정 |
---
