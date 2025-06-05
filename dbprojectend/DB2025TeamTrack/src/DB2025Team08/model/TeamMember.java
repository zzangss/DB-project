package DB2025Team08.model;

import java.time.LocalDateTime;

/**
 * 팀 멤버 정보를 나타내는 모델 클래스.
 */
public class TeamMember {
    private int teamId;
    private int userId;
    private LocalDateTime joinedAt;

    /**
     * 기본 생성자.
     */
    public TeamMember() {}

    /**
     * 모든 필드를 초기화하는 생성자.
     *
     * @param teamId   팀 ID
     * @param userId   사용자 ID
     * @param joinedAt 가입 시각
     */
    public TeamMember(int teamId, int userId, LocalDateTime joinedAt) {
        this.teamId = teamId;
        this.userId = userId;
        this.joinedAt = joinedAt;
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
     * 가입 시각을 반환한다.
     *
     * @return 가입 시각
     */
    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    /**
     * 가입 시각을 설정한다.
     *
     * @param joinedAt 가입 시각
     */
    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }
    
    @Override
    public String toString() {
        return "TeamMember{" +
               ", userId=" + userId +
               ", joinedAt=" + joinedAt +
               '}';
    }

}
