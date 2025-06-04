package DB2025Team08.model;

public class Contribution {
    private int teamId;
    private String name;
    private double rawScore;
    private double percentage;

    // 기본 생성자
    public Contribution() { }

    // 필드 초기화용 생성자
    public Contribution(int teamId, String name, double rawScore, double percentage) {
        this.teamId = teamId;
        this.name = name;
        this.rawScore = rawScore;
        this.percentage = percentage;
    }

    // Getter / Setter
    public int getTeamId() {
        return teamId;
    }
    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public double getRawScore() {
        return rawScore;
    }
    public void setRawScore(double rawScore) {
        this.rawScore = rawScore;
    }

    public double getPercentage() {
        return percentage;
    }
    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "ContributionSummary[" +
               "teamId=" + teamId +
               ", name='" + name + '\'' +
               ", rawScore=" + rawScore +
               ", percentage=" + percentage +
               ']';
    }
}
