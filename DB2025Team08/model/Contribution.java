package DB2025Team08.model;

/**
 * 팀 기여도 정보를 나타내는 모델 클래스.
 */
public class Contribution {
    private int teamId;
    private String name;
    private double rawScore;
    private double percentage;

    /**
     * 기본 생성자.
     */
    public Contribution() { }

    /**
     * 필드를 초기화하는 생성자.
     *
     * @param teamId      팀 ID
     * @param name        사용자 이름
     * @param rawScore    원점수
     * @param percentage  기여도 백분율
     */
    public Contribution(int teamId, String name, double rawScore, double percentage) {
        this.teamId = teamId;
        this.name = name;
        this.rawScore = rawScore;
        this.percentage = percentage;
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
     * 사용자 이름을 반환한다.
     *
     * @return 사용자 이름
     */
    public String getName() {
        return name;
    }

    /**
     * 사용자 이름을 설정한다.
     *
     * @param name 사용자 이름
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 원점수를 반환한다.
     *
     * @return 원점수
     */
    public double getRawScore() {
        return rawScore;
    }

    /**
     * 원점수를 설정한다.
     *
     * @param rawScore 원점수
     */
    public void setRawScore(double rawScore) {
        this.rawScore = rawScore;
    }

    /**
     * 기여도 백분율을 반환한다.
     *
     * @return 기여도 백분율
     */
    public double getPercentage() {
        return percentage;
    }

    /**
     * 기여도 백분율을 설정한다.
     *
     * @param percentage 기여도 백분율
     */
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
