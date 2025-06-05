package meeting_feedback.dto;

import java.time.LocalDate;

public class MeetingDTO {
    private int teamId;
    private LocalDate meetingDate;
    private String content;
    private String decision;

    public MeetingDTO(int teamId, LocalDate meetingDate, String content, String decision) {
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