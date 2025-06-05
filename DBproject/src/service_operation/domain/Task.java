package service_operation.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Task {
    private int taskId;
    private String title;
    private String status;
    private LocalDate dueDate;

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

    // Getters
    public int getTaskId() { return taskId; }
    public String getTitle() { return title; }
    public String getStatus() { return status; }
    public LocalDate getDueDate() { return dueDate; }

    public void setStatus(String status) { this.status = status; }
}