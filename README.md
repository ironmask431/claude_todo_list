# 📝 Todo List Application

세션 기반 인증을 사용하는 간단하고 우아한 Todo List 웹 애플리케이션입니다.

## 🛠 기술 스택

### Backend
- **Java**: 21
- **Kotlin**: 1.9.25
- **Spring Boot**: 3.5.7
- **Spring Data JPA**: ORM 및 데이터베이스 연동
- **Spring Security Crypto**: BCrypt 비밀번호 암호화
- **H2 Database**: 인메모리 데이터베이스
- **Gradle**: 8.14.3

### Frontend
- **Thymeleaf**: 서버 사이드 템플릿 엔진
- **HTML5/CSS3**: 모던하고 반응형 UI

## ✨ 주요 기능

### 🔐 회원 관리
- ✅ 회원가입
  - 이메일 중복 체크
  - BCrypt 비밀번호 암호화
  - 닉네임 설정
- ✅ 로그인/로그아웃
  - 세션 기반 인증
  - 로그인 상태 유지

### 📋 Todo 관리
- ✅ Todo CRUD
  - Todo 생성 (제목, 설명)
  - Todo 조회 (사용자별 필터링)
  - Todo 수정
  - Todo 삭제
  - Todo 완료/미완료 토글
- ✅ 사용자별 Todo 격리
  - 로그인한 사용자만 자신의 Todo 접근 가능
  - 다른 사용자의 Todo는 조회/수정/삭제 불가

### 🎨 UI/UX
- ✅ 모던하고 직관적인 디자인
- ✅ 그라데이션 배경과 부드러운 애니메이션
- ✅ 사용자 프로필 표시 (닉네임, 아바타)
- ✅ 반응형 디자인

## 📁 프로젝트 구조

```
claude_todo_list/
├── src/
│   ├── main/
│   │   ├── kotlin/org/example/claude_todo_list/
│   │   │   ├── controller/
│   │   │   │   ├── AuthController.kt          # 회원가입, 로그인, 로그아웃
│   │   │   │   ├── HomeController.kt          # 홈 페이지 리다이렉션
│   │   │   │   ├── TodoController.kt          # REST API 엔드포인트
│   │   │   │   └── TodoViewController.kt      # Thymeleaf 뷰 컨트롤러
│   │   │   ├── dto/
│   │   │   │   ├── LoginRequest.kt           # 로그인 요청 DTO
│   │   │   │   ├── SignupRequest.kt          # 회원가입 요청 DTO
│   │   │   │   ├── TodoRequest.kt            # Todo 생성 요청 DTO
│   │   │   │   ├── TodoResponse.kt           # Todo 응답 DTO
│   │   │   │   └── TodoUpdateRequest.kt      # Todo 수정 요청 DTO
│   │   │   ├── entity/
│   │   │   │   ├── Todo.kt                   # Todo 엔티티 (ManyToOne -> User)
│   │   │   │   └── User.kt                   # User 엔티티 (OneToMany -> Todo)
│   │   │   ├── exception/
│   │   │   │   └── GlobalExceptionHandler.kt # 전역 예외 처리
│   │   │   ├── repository/
│   │   │   │   ├── TodoRepository.kt         # Todo JPA Repository
│   │   │   │   └── UserRepository.kt         # User JPA Repository
│   │   │   ├── service/
│   │   │   │   ├── TodoService.kt            # Todo 비즈니스 로직
│   │   │   │   └── UserService.kt            # User 비즈니스 로직
│   │   │   └── ClaudeTodoListApplication.kt  # Spring Boot 메인 클래스
│   │   └── resources/
│   │       ├── templates/
│   │       │   ├── create.html               # Todo 생성 페이지
│   │       │   ├── edit.html                 # Todo 수정 페이지
│   │       │   ├── list.html                 # Todo 목록 페이지
│   │       │   ├── login.html                # 로그인 페이지
│   │       │   └── signup.html               # 회원가입 페이지
│   │       └── application.yml               # 애플리케이션 설정
│   └── test/
├── build.gradle                               # Gradle 빌드 설정
├── settings.gradle                            # Gradle 프로젝트 설정
└── README.md
```

## 🗄 데이터베이스 스키마

### Users 테이블
```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,    -- BCrypt 암호화
    nickname VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
);
```

### Todos 테이블
```sql
CREATE TABLE todos (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    is_done BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
```

## 🚀 실행 방법

### 1. 사전 요구사항
- Java 21 이상 설치
- Git 설치 (선택사항)

### 2. 프로젝트 클론
```bash
git clone <repository-url>
cd claude_todo_list
```

### 3. 빌드
```bash
./gradlew clean build
```

Windows 사용자:
```bash
gradlew.bat clean build
```

### 4. 실행
```bash
./gradlew bootRun
```

Windows 사용자:
```bash
gradlew.bat bootRun
```

또는 빌드된 JAR 파일 직접 실행:
```bash
java -jar build/libs/claude_todo_list-0.0.1-SNAPSHOT.jar
```

### 5. 접속
브라우저에서 http://localhost:8080 접속

## 📱 사용 방법

### 1단계: 회원가입
1. 홈페이지 접속 시 로그인 페이지로 리다이렉트
2. "회원가입" 링크 클릭
3. 이메일, 비밀번호, 닉네임 입력
4. 회원가입 완료

### 2단계: 로그인
1. 등록한 이메일과 비밀번호로 로그인
2. 로그인 성공 시 Todo 목록 페이지로 이동

### 3단계: Todo 관리
1. **Todo 생성**: "+ New Todo" 버튼 클릭
2. **Todo 완료**: 체크박스 클릭으로 완료 상태 토글
3. **Todo 수정**: "Edit" 버튼 클릭
4. **Todo 삭제**: "Delete" 버튼 클릭

### 4단계: 로그아웃
- 우측 상단의 "로그아웃" 버튼 클릭

## 🔒 보안 기능

- **비밀번호 암호화**: BCrypt를 사용한 안전한 비밀번호 저장
- **세션 기반 인증**: 서버 세션을 통한 사용자 인증 관리
- **사용자 격리**: 각 사용자는 자신의 Todo만 접근 가능
- **SQL Injection 방지**: JPA를 통한 파라미터화된 쿼리 사용

## 🎯 API 엔드포인트

### 인증 API
- `GET /` - 홈페이지 (로그인 여부에 따라 리다이렉트)
- `GET /signup` - 회원가입 페이지
- `POST /signup` - 회원가입 처리
- `GET /login` - 로그인 페이지
- `POST /login` - 로그인 처리
- `GET /logout` - 로그아웃 처리

### Todo 웹 페이지
- `GET /todos` - Todo 목록 조회
- `GET /todos/create` - Todo 생성 폼
- `POST /todos/create` - Todo 생성 처리
- `GET /todos/{id}/edit` - Todo 수정 폼
- `POST /todos/{id}/edit` - Todo 수정 처리
- `POST /todos/{id}/toggle` - Todo 완료 상태 토글
- `POST /todos/{id}/delete` - Todo 삭제

### REST API
- `GET /api/todos` - Todo 목록 조회 (JSON)
- `POST /api/todos` - Todo 생성 (JSON)
- `GET /api/todos/{id}` - Todo 상세 조회 (JSON)
- `PUT /api/todos/{id}` - Todo 수정 (JSON)
- `DELETE /api/todos/{id}` - Todo 삭제

## 🛠 개발 도구

### H2 Console
- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (비워둠)

### Gradle 태스크
```bash
# 빌드
./gradlew build

# 테스트 실행
./gradlew test

# 실행
./gradlew bootRun

# 클린 빌드
./gradlew clean build

# 의존성 새로고침
./gradlew build --refresh-dependencies
```

## 📝 라이선스

This project is open source and available under the [MIT License](LICENSE).

## 👤 개발자

개발 문의 및 이슈 리포트는 GitHub Issues를 이용해주세요.

## 🙏 감사의 말

이 프로젝트는 Spring Boot와 Kotlin을 사용한 웹 애플리케이션 학습 목적으로 제작되었습니다.

---

⭐ 이 프로젝트가 도움이 되었다면 Star를 눌러주세요!
