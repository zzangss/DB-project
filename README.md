2025 1학기 데이터베이스 프로젝트
## 🤝 GitHub 협업 가이드

---

### 1. 협업 절차

1. **레포지터리 클론**  
   - 팀 레포를 클론하여 사용

2. **브랜치 생성**  
   - `main` 브랜치에서 직접 작업하지 않고, 반드시 새로운 브랜치를 생성하여 작업  
   - 브랜치 이름은 아래 네이밍 규칙을 따른다

3. **작업 및 커밋**  
   - 기능 개발, 버그 수정 등을 진행하고 커밋

4. **푸시 & Pull Request(PR)**  
   - 작업한 브랜치를 원격에 푸시한 뒤, `main` 브랜치로 PR을 생성 
   - PR에는 작업한 내용을 간단히 작성

5. **리뷰 & 머지**  
   - 리뷰한 후, 문제가 없다면 `main` 브랜치로 병합

---

### 2. 브랜치 네이밍 규칙

| Prefix      | 설명                             | 예시                    |
|-------------|----------------------------------|-------------------------|
| `feature/`  | 새로운 기능 추가                 | `feature/login-page`   |
| `fix/`      | 버그 수정                        | `fix/login-error`      |
| `refactor/` | 리팩토링 (기능 변경 없음)        | `refactor/user-service`|
| `chore/`    | 설정, 문서 등 사소한 변경        | `chore/update-readme`  |

> 🔸 브랜치 이름은 가능하면 **소문자와 하이픈(-)** 을 사용해 작성

---

### 3. 커밋 & PR

- **커밋 메시지**: 작업의 목적이 드러나도록 간결하게 작성합니다.  
  예)  fix: 로그인 실패 시 메시지 표시, feat: 게시판 필터 기능 추가
  
- **PR 설명**:  
무엇을 *왜* 수정했는지 요약해서 작성!  
  
