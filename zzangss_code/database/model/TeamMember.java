package database.model;

import java.time.LocalDateTime;

public class TeamMember {
    private int teamId;
    private int userId;
    private LocalDateTime joinedAt;

    // 기본 생성자
    public TeamMember() {}

    // 전체 필드 생성자
    public TeamMember(int teamId, int userId, LocalDateTime joinedAt) {
        this.teamId = teamId;
        this.userId = userId;
        this.joinedAt = joinedAt;
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

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    @Override
    public String toString() {
        return "TeamMember{" +
               "teamId=" + teamId +
               ", userId=" + userId +
               ", joinedAt=" + joinedAt +
               '}';
    }
}

