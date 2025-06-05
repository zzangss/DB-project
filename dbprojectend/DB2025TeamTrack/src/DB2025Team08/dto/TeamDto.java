package DB2025Team08.dto;

import java.time.LocalDate;

/**
 * 팀 생성을 위한 DTO 클래스.
 */
public class TeamDto {
    /**
     * 팀 이름
     */
    private String teamName;

    /**
     * 과제 주제
     */
    private String subject;

    /**
     * 마감일
     */
    private LocalDate deadline;

    /**
     * 팀장 사용자 ID
     */
    private int leaderId;

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
}
