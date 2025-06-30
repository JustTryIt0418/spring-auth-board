# Spring Auth Board

Spring Security 기반의 사용자 인증/인가와 게시판 기능이 포함된 백엔드 프로젝트입니다.  
JWT 기반의 로그인 시스템과 게시글 CRUD, 댓글 기능, 페이징, 검색 기능을 포함하고 있습니다.

## 주요 기능

### 인증 & 인가
- 회원가입 / 로그인 / 로그아웃
- JWT 기반 Access/Refresh Token 발급 및 갱신
- 로그인된 사용자 정보 조회
- Redis를 통한 RefreshToken 저장 및 블랙리스트 처리
- 사용자 권한(Role)에 따른 접근 제어

### 게시판
- 게시글 목록 조회 (검색 및 정렬, 페이징 지원)
- 게시글 상세 조회
- 게시글 등록 / 수정 / 삭제 (작성자 또는 관리자만 가능)
- 댓글 등록 / 삭제 (작성자 또는 관리자만 가능)
- 게시글과 댓글은 `ACTIVE`/`DELETED` 상태로 soft delete 처리

---

## 사용 기술 스택

| 구분 | 기술 |
|------|------|
| Language | Java 17 |
| Framework | Spring Boot 3.x |
| Security | Spring Security, JWT |
| ORM | Spring Data JPA, QueryDSL |
| DB | PostgreSQL |
| Cache | Redis |
| Build Tool | Gradle |
| 문서화 | Swagger 3 (springdoc-openapi) |
| 컨테이너 | Docker, Docker Compose |

---

## 프로젝트 구조

```
spring-auth-board/
├── domain/
│   ├── user/                 # 사용자 도메인
│   ├── post/                 # 게시글 도메인
│   ├── comment/              # 댓글 도메인
├── security/                 # 인증/인가 관련 설정 및 필터
├── common/                   # 공통 예외처리, 응답객체, 유틸
├── config/                   # Redis, Security, QueryDSL, Swagger 등 설정
├── application.yml           # 환경 설정
```

---

## API 문서

실행 후 브라우저에서 다음 주소로 Swagger UI 확인 가능:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 개발자

| 이름 | GitHub |
|------|--------|
| 박정훈 | [https://github.com/JustTryIt0418](https://github.com/JustTryIt0418) |

