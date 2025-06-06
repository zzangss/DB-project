package DB2025Team08.dto;

import java.time.LocalDate;

/**
 * 회의 정보를 전달하는 DTO 클래스.
 */
public class MeetingDto {
    /**
     * 팀 ID
     */
    private int teamId;

    /**
     * 회의 날짜
     */
    private LocalDate meetingDate;

    /**
     * 회의 내용
     */
    private String content;

    /**
     * 회의 결정 사항
     */
    private String decision;

    /**
     * MeetingDto 객체를 생성한다.
     *
     * @param teamId 팀 ID
     * @param meetingDate 회의 날짜
     * @param content 회의 내용
     * @param decision 결정 사항
     */
    public MeetingDto(int teamId, LocalDate meetingDate, String content, String decision) {
        this.teamId = teamId;
        this.meetingDate = meetingDate;
        this.content = content;
        this.decision = decision;
    }

    /**
     * 팀 ID를 반환한다.
     *
     * @return 팀 ID
     */
    public int getTeamId() { return teamId; }

    /**
     * 회의 날짜를 반환한다.
     *
     * @return 회의 날짜
     */
    public LocalDate getMeetingDate() { return meetingDate; }

    /**
     * 회의 내용을 반환한다.
     *
     * @return 회의 내용
     */
    public String getContent() { return content; }

    /**
     * 결정 사항을 반환한다.
     *
     * @return 결정 사항
     */
    public String getDecision() { return decision; }
}
