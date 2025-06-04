package DB2025Team08.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Task {
    private int taskId;
    private String title;
    private LocalDate dueDate;
    private String status;
    private int assignedTo;
    private int teamId;

    // 기본 생성자
    public Task() {}

    // 전체 필드 생성자
    public Task(int taskId, String title, LocalDate dueDate, String status, int assignedTo, int teamId) {
        this.taskId = taskId;
        this.title = title;
        this.dueDate = dueDate;
        this.status = status;
        this.assignedTo = assignedTo;
        this.teamId = teamId;
    }
    
    public Task(int taskId, String title, String status, LocalDate dueDate) {
        this.taskId = taskId;
        this.title = title;
        this.status = status;
        this.dueDate = dueDate;
    }

    // 비즈니스 로직
    public boolean isDueTomorrow(LocalDate today) {
        return ChronoUnit.DAYS.between(today, dueDate) == 1;
    }

    public String determineNewStatus(LocalDate today) {
        if (!"진행 중".equals(status)) return null;

        long daysLate = ChronoUnit.DAYS.between(dueDate, today);

        if (!today.isAfter(dueDate)) {
            return "완료";
        } else if (daysLate <= 3) {
            return "지각 제출";
        } else {
            return "미제출";
        }
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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
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
