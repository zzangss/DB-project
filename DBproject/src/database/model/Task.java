package database.model;

import java.time.LocalDateTime;

public class Task {
    private int taskId;
    private String title;
    private LocalDateTime dueDate;
    private String status;
    private int assignedTo;
    private int teamId;

    // 기본 생성자
    public Task() {}

    // 전체 필드 생성자
    public Task(int taskId, String title, LocalDateTime dueDate, String status, int assignedTo, int teamId) {
        this.taskId = taskId;
        this.title = title;
        this.dueDate = dueDate;
        this.status = status;
        this.assignedTo = assignedTo;
        this.teamId = teamId;
    }

    // getters & setters
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(int assignedTo) {
        this.assignedTo = assignedTo;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    @Override
    public String toString() {
        return "Task{" +
               "taskId=" + taskId +
               ", title='" + title + '\'' +
               ", dueDate=" + dueDate +
               ", status='" + status + '\'' +
               ", assignedTo=" + assignedTo +
               ", teamId=" + teamId +
               '}';
    }
}
