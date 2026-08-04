# Team Project Manager

2025년 1학기 데이터베이스 수업에서 진행한 팀 프로젝트입니다. Java Swing과 MySQL을 사용해 팀 구성부터 역할·일정·피드백 관리까지 하나의 데스크톱 애플리케이션으로 구현했습니다.

## 주요 기능

- 회원가입 및 로그인
- 팀 생성, 팀원 추가와 팀 정보 관리
- 팀원별 역할 배정
- 과제 등록과 진행 상태 관리
- 회의 일정 등록 및 피드백 작성
- 팀원별 기여도 조회
- 마이페이지에서 참여 팀과 담당 업무 확인

## 기술 구성

- Java 11+
- Java Swing
- MySQL 8
- JDBC
- DAO, DTO, Model, Service 계층 분리

## 프로젝트 구조

```text
Team08_final/
├── create.sql                 # 데이터베이스·테이블·샘플 데이터 생성
├── dropdb.sql                 # 데이터베이스 제거
├── DBProject/
│   ├── src/
│   │   ├── DB2025Team08/      # DAO, DTO, Model, Service
│   │   └── DB2025Team08GUI/   # Swing 화면
│   └── lib/                   # MySQL JDBC 드라이버
└── doc/                       # 생성된 Javadoc
```

## 실행 방법

1. MySQL 8과 Java 11 이상을 준비합니다.
2. MySQL에서 `Team08_final/create.sql`을 실행합니다.
3. Eclipse에서 `Team08_final/DBProject`를 프로젝트로 불러옵니다.
4. `src/DB2025Team08GUI/MainAppGUI.java`를 실행합니다.

기본 접속 정보는 수업용 로컬 환경에 맞춰 `ConnectionManager.java`와 `create.sql`에 들어 있습니다.

## 주의사항

이 저장소는 데이터베이스 설계와 JDBC 연동 학습을 위한 교육용 프로젝트입니다. 샘플 계정과 고정된 로컬 DB 접속값을 사용하며, 비밀번호 해싱 등 실제 서비스에 필요한 보안 처리는 포함하지 않았습니다. 공개 서버나 실제 사용자 데이터를 다루는 환경에 그대로 배포하면 안 됩니다.
