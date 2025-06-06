package DB2025Team08.model;

/**
 * 팀 내 역할 할당 정보를 나타내는 모델 클래스.
 */
public class RoleAssignment {
    private int teamId;
    private int userId;
    private String roleName;

    /**
     * 기본 생성자.
     */
    public RoleAssignment() {}

    /**
     * 모든 필드를 초기화하는 생성자.
     *
     * @param teamId 팀 ID
     * @param userId 사용자 ID
     * @param roleName 역할 이름
     */
    public RoleAssignment(int teamId, int userId, String roleName) {
        this.teamId = teamId;
        this.userId = userId;
        this.roleName = roleName;
    }

    /**
     * 팀 ID를 반환한다.
     *
     * @return 팀 ID
     */
    public int getTeamId() {
        return teamId;
    }

    /**
     * 팀 ID를 설정한다.
     *
     * @param teamId 팀 ID
     */
    public void setTeamId(int teamId) {
        this.teamId = teamId;
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
     * 역할 이름을 반환한다.
     *
     * @return 역할 이름
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 역할 이름을 설정한다.
     *
     * @param roleName 역할 이름
     */
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
