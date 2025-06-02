package service_operation.dto;

public class Task_DTO {
    private int taskId;
    private String title;
    private String status;
    private String dueDate;

    public Task_DTO(int taskId, String title, String status, String dueDate) {
        this.taskId = taskId;
        this.title = title;
        this.status = status;
        this.dueDate = dueDate;
    }

    // Getters and Setters
    public int getTaskId() { return taskId; }
    public void setTaskId(int taskId) { this.taskId = taskId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDueDate() { return dueDate; }
    public void setDueDate(String dueDate) { this.dueDate = dueDate; }
}