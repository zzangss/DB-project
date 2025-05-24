package database.model;

public class RoleAssignment {
    private int teamId;
    private int userId;
    private String roleName;

    // 기본 생성자
    public RoleAssignment() {}

    // 전체 필드 생성자
    public RoleAssignment(int teamId, int userId, String roleName) {
        this.teamId = teamId;
        this.userId = userId;
        this.roleName = roleName;
    }

    // getters & setters
    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "RoleAssignment{" +
               "teamId=" + teamId +
               ", userId=" + userId +
               ", roleName='" + roleName + '\'' +
               '}';
    }
}
