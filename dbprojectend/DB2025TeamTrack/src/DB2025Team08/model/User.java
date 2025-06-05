package DB2025Team08.model;

import java.time.LocalDateTime;

/**
 * 사용자 정보를 나타내는 모델 클래스.
 */
public class User {
    private int userId;
    private String email;
    private String password;
    private String name;
    private String role;
    private LocalDateTime createdAt;

    /**
     * 기본 생성자.
     */
    public User() {}

    /**
     * 사용자 ID만 설정하는 생성자.
     *
     * @param userId 사용자 ID
     */
    public User(int userId) {
        this.userId = userId;
    }
    
    /**
     * 모든 필드를 초기화하는 생성자.
     *
     * @param userId    사용자 ID
     * @param email     이메일
     * @param password  비밀번호
     * @param name      이름
     * @param role      역할
     * @param createdAt 생성 시각
     */
    public User(int userId, String email, String password, String name, String role, LocalDateTime createdAt) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
        this.createdAt = createdAt;
    }
    
    /**
     * 사용자 정보를 문자열로 반환한다.
     *
     * @return "이름 (이메일)" 형식 문자열
     */
    @Override
    public String toString() {
        return name + " (" + email + ")";
    }

    /**
     * 사용자 ID를 반환한다.
     *
     * @return 사용자 ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * 사용자 ID를 설정한다.
     *
     * @param userId 사용자 ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * 이메일을 반환한다.
     *
     * @return 이메일
     */
    public String getEmail() {
        return email;
    }

    /**
     * 이메일을 설정한다.
     *
     * @param email 이메일
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 비밀번호를 반환한다.
     *
     * @return 비밀번호
     */
    public String getPassword() {
        return password;
    }

    /**
     * 비밀번호를 설정한다.
     *
     * @param password 비밀번호
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 이름을 반환한다.
     *
     * @return 이름
     */
    public String getName() {
        return name;
    }

    /**
     * 이름을 설정한다.
     *
     * @param name 이름
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 역할을 반환한다.
     *
     * @return 역할
     */
    public String getRole() {
        return role;
    }

    /**
     * 역할을 설정한다.
     *
     * @param role 역할
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * 생성 시각을 반환한다.
     *
     * @return 생성 시각
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * 생성 시각을 설정한다.
     *
     * @param createdAt 생성 시각
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
