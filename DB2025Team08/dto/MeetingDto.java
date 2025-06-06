package DB2025Team08.dto;

import java.time.LocalDate;

public class MeetingDto {
    private int teamId;
    private LocalDate meetingDate;
    private String content;
    private String decision;

    public MeetingDto(int teamId, LocalDate meetingDate, String content, String decision) {
        this.teamId = teamId;
        this.meetingDate = meetingDate;
        this.content = content;
        this.decision = decision;
    }

    public int getTeamId() { return teamId; }
    public LocalDate getMeetingDate() { return meetingDate; }
    public String getContent() { return content; }
    public String getDecision() { return decision; }
}