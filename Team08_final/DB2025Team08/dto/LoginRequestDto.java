package DB2025Team08.dto;

/**
 * 로그인 요청 정보를 전달하는 DTO 클래스.
 */
public class LoginRequestDto {
    /**
     * 사용자 이메일
     */
    private String email;

    /**
     * 사용자 비밀번호
     */
    private String password;

    /**
     * 로그인 요청 DTO를 생성한다.
     *
     * @param email    사용자의 이메일
     * @param password 사용자의 비밀번호
     */
    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * 이메일을 반환한다.
     *
     * @return 사용자의 이메일
     */
    public String getEmail() {
        return email;
    }

    /**
     * 비밀번호를 반환한다.
     *
     * @return 사용자의 비밀번호
     */
    public String getPassword() {
        return password;
    }
}
