import java.sql.SQLException;
import java.time.LocalDate;

import dao.TaskDAO;
import domain.Task;

public class TaskService {
    private final TaskDAO dao;

    public TaskService(TaskDAO dao) {
        this.dao = dao;
    }

    public void notifyDueTasksAndUpdateStatuses() throws SQLException {
        for (Task task : dao.getTasksDueTomorrow()) {
            System.out.println("[알림] 내일 '" + task.getTitle() + "' 과제가 마감됩니다! (마감일: " + task.getDueDate() + ")");
        }

        for (Task task : dao.getAllTasks()) {
            String newStatus = task.determineNewStatus(LocalDate.now());
            if (newStatus != null && !newStatus.equals(task.getStatus())) {
                dao.updateTaskStatus(task.getTaskId(), newStatus);
                System.out.println("과제 ID " + task.getTaskId() + " 상태가 '" + newStatus + "'로 변경되었습니다.");
            }
        }
    }
}