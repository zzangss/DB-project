package database.dto;

public class UserDto {
    private String email;
    private String password;
    private String name;
    private String role;

    public UserDto(String email, String password, String name, String role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = role;
    }

    // Getter 생략 가능, 필요 시 추가
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getRole() { return role; }
}