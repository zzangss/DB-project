package database;

public class Contribution {
    private int userId;
    private int teamId;
    private int submittedCount;
    private int attendedCount;
    private int bonus;
    private double percentage;

    // 기본 생성자
    public Contribution() {}

    // 전체 필드 생성자
    public Contribution(int userId, int teamId, int submittedCount, int attendedCount, int bonus, double percentage) {
        this.userId = userId;
        this.teamId = teamId;
        this.submittedCount = submittedCount;
        this.attendedCount = attendedCount;
        this.bonus = bonus;
        this.percentage = percentage;
    }

    // getters & setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getSubmittedCount() {
        return submittedCount;
    }

    public void setSubmittedCount(int submittedCount) {
        this.submittedCount = submittedCount;
    }

    public int getAttendedCount() {
        return attendedCount;
    }

    public void setAttendedCount(int attendedCount) {
        this.attendedCount = attendedCount;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "Contribution{" +
               "userId=" + userId +
               ", teamId=" + teamId +
               ", submittedCount=" + submittedCount +
               ", attendedCount=" + attendedCount +
               ", bonus=" + bonus +
               ", percentage=" + percentage +
               '}';
    }
}
