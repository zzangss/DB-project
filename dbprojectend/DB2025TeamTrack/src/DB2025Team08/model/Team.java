package DB2025Team08.model;

import java.time.LocalDate;

/**
 * 팀 정보를 나타내는 모델 클래스.
 */
public class Team {
    private int teamId;
    private String teamName;
    private String subject;
    private LocalDate deadline;
    private int leaderId;

    /**
     * 기본 생성자.
     */
    public Team() {}

    /**
     * 모든 필드를 초기화하는 생성자.
     *
     * @param teamId    팀 ID
     * @param teamName  팀 이름
     * @param subject   과제 주제
     * @param deadline  마감일
     * @param leaderId  팀장 사용자 ID
     */
    public Team(int teamId, String teamName, String subject, LocalDate deadline, int leaderId) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.subject = subject;
        this.deadline = deadline;
        this.leaderId = leaderId;
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
     * 팀 이름을 반환한다.
     *
     * @return 팀 이름
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * 팀 이름을 설정한다.
     *
     * @param teamName 팀 이름
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     * 과제 주제를 반환한다.
     *
     * @return 과제 주제
     */
    public String getSubject() {
        return subject;
    }

    /**
     * 과제 주제를 설정한다.
     *
     * @param subject 과제 주제
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * 마감일을 반환한다.
     *
     * @return 마감일
     */
    public LocalDate getDeadline() {
        return deadline;
    }

    /**
     * 마감일을 설정한다.
     *
     * @param deadline 마감일
     */
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    /**
     * 팀장 사용자 ID를 반환한다.
     *
     * @return 팀장 ID
     */
    public int getLeaderId() {
        return leaderId;
    }

    /**
     * 팀장 사용자 ID를 설정한다.
     *
     * @param leaderId 팀장 ID
     */
    public void setLeaderId(int leaderId) {
        this.leaderId = leaderId;
    }
    
    @Override
    public String toString() {
        return teamName;  // 혹은 "팀명 (subject)" 같이 원하는 형식으로
    }

}
