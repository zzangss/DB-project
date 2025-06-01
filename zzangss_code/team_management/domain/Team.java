package team_management.domain;

import java.time.LocalDate;

public class Team {
    private int teamId;
    private String teamName;
    private String subject;
    private LocalDate deadline;
    private int leaderId;

    public Team(int teamId, String teamName, String subject, LocalDate deadline, int leaderId) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.subject = subject;
        this.deadline = deadline;
        this.leaderId = leaderId;
    }
    
    public int getTeamId() {return teamId; }
	public String getTeamName() { return teamName; }
    public String getSubject() { return subject; }
    public LocalDate getDeadline() { return deadline; }
    public int getLeaderId() { return leaderId; }

}