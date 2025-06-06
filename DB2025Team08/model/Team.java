package DB2025Team08.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Team {
    private int teamId;
    private String teamName;
    private String subject;
    private LocalDate deadline;
    private int leaderId;

    // 기본 생성자
    public Team() {}

    // 전체 필드 생성자
    public Team(int teamId, String teamName, String subject, LocalDate deadline, int leaderId) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.subject = subject;
        this.deadline = deadline;
        this.leaderId = leaderId;
    }

    // getters & setters
    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public int getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(int leaderId) {
        this.leaderId = leaderId;
    }

    @Override
    public String toString() {
        return "Team{" +
               "teamId=" + teamId +
               ", teamName='" + teamName + '\'' +
               ", subject='" + subject + '\'' +
               ", deadline=" + deadline +
               ", leaderId=" + leaderId +
               '}';
    }
}
