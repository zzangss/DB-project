package DB2025Team08.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * 과제를 나타내는 모델 클래스.
 */
public class Task {
    private int taskId;
    private String title;
    private LocalDate dueDate;
    private String status;
    private int assignedTo;
    private int teamId;

    /**
     * 기본 생성자.
     */
    public Task() {}

    /**
     * 전체 필드를 초기화하는 생성자.
     *
     * @param taskId       과제 ID
     * @param title        과제 제목
     * @param dueDate      과제 마감일
     * @param status       과제 상태
     * @param assignedTo   과제를 할당받은 사용자 ID
     * @param teamId       소속된 팀 ID
     */
    public Task(int taskId, String title, LocalDate dueDate, String status, int assignedTo, int teamId) {
        this.taskId = taskId;
        this.title = title;
        this.dueDate = dueDate;
        this.status = status;
        this.assignedTo = assignedTo;
        this.teamId = teamId;
    }

    /**
     * 일부 필드를 초기화하는 생성자.
     *
     * @param taskId  과제 ID
     * @param title   과제 제목
     * @param status  과제 상태
     * @param dueDate 과제 마감일
     */
    public Task(int taskId, String title, String status, LocalDate dueDate) {
        this.taskId = taskId;
        this.title = title;
        this.status = status;
        this.dueDate = dueDate;
    }

    /**
     * 마감일이 오늘 기준으로 내일인지 확인한다.
     *
     * @param today 오늘 날짜
     * @return 내일 마감이면 true, 아니면 false
     */
    public boolean isDueTomorrow(LocalDate today) {
        return ChronoUnit.DAYS.between(today, dueDate) == 1;
    }

    /**
     * 오늘 날짜를 기준으로 새로운 상태를 결정한다.
     * 상태가 "진행 중"이 아닐 경우 null을 반환한다.
     * 
     * @param today 오늘 날짜
     * @return 새로운 상태("완료", "지각 제출", "미제출") 또는 null
     */
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

    /**
     * 과제 ID를 반환한다.
     *
     * @return 과제 ID
     */
    public int getTaskId() {
        return taskId;
    }

    /**
     * 과제 ID를 설정한다.
     *
     * @param taskId 과제 ID
     */
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    /**
     * 과제 제목을 반환한다.
     *
     * @return 과제 제목
     */
    public String getTitle() {
        return title;
    }

    /**
     * 과제 제목을 설정한다.
     *
     * @param title 과제 제목
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 마감일을 반환한다.
     *
     * @return 마감일
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * 마감일을 설정한다.
     *
     * @param dueDate 마감일
     */
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * 상태를 반환한다.
     *
     * @return 과제 상태
     */
    public String getStatus() {
        return status;
    }

    /**
     * 상태를 설정한다.
     *
     * @param status 과제 상태
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 과제를 할당받은 사용자 ID를 반환한다.
     *
     * @return 사용자 ID
     */
    public int getAssignedTo() {
        return assignedTo;
    }

    /**
     * 과제를 할당받은 사용자 ID를 설정한다.
     *
     * @param assignedTo 사용자 ID
     */
    public void setAssignedTo(int assignedTo) {
        this.assignedTo = assignedTo;
    }

    /**
     * 팀 ID를 반환한다.
     *
     * @return 팀 ID
     */
    public int getTeamId() {
        return teamId;
    }

    /**
     * 팀 ID를 설정한다.
     *
     * @param teamId 팀 ID
     */
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
