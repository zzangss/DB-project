package DB2025Team08.dto;

/**
 * 사용자 정보를 전달하는 DTO 클래스.
 */
public class UserDto {
    /**
     * 사용자 이메일
     */
    private String email;

    /**
     * 사용자 비밀번호
     */
    private String password;

    /**
     * 사용자 이름
     */
    private String name;

    /**
     * 사용자 역할
     */
    private String role;

    /**
     * 기본 생성자.
     */
    public UserDto() {
    }

    /**
     * 모든 필드를 초기화하는 생성자.
     *
     * @param email    사용자 이메일
     * @param password 사용자 비밀번호
     * @param name     사용자 이름
     * @param role     사용자 역할
     */
    public UserDto(String email, String password, String name, String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    /**
     * 이메일을 반환한다.
     *
     * @return 사용자 이메일
     */
    public String getEmail() {
        return email;
    }

    /**
     * 이메일을 설정한다.
     *
     * @param email 사용자 이메일
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 비밀번호를 반환한다.
     *
     * @return 사용자 비밀번호
     */
    public String getPassword() {
        return password;
    }

    /**
     * 비밀번호를 설정한다.
     *
     * @param password 사용자 비밀번호
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 이름을 반환한다.
     *
     * @return 사용자 이름
     */
    public String getName() {
        return name;
    }

    /**
     * 이름을 설정한다.
     *
     * @param name 사용자 이름
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 역할을 반환한다.
     *
     * @return 사용자 역할
     */
    public String getRole() {
        return role;
    }

    /**
     * 역할을 설정한다.
     *
     * @param role 사용자 역할
     */
    public void setRole(String role) {
        this.role = role;
    }
}
