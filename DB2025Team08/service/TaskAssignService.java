package DB2025Team08.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import DB2025Team08.ConnectionManager;
import DB2025Team08.dao.TeamDao;
import DB2025Team08.dao.TaskDao;
import DB2025Team08.dto.MyPageDto;
import DB2025Team08.model.Team;
import DB2025Team08.model.User;

public class TaskAssignService {
    private TaskDao taskDao;

    public TaskAssignService() {
        this.taskDao = new TaskDao();
    }
    
    //팀 id, 담당자 ID, 제목, 마감일 입력
    public boolean assignTask(int teamId, int assignedTo, String title, LocalDateTime dueDate) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Connection conn = null;

        try {
            // 5) 트랜잭션 시작 (쓰기 작업)
            conn = ConnectionManager.getConnection();
            conn.setAutoCommit(false);

            // 여기서 Dao에 Connection을 넘겨서 작업
            taskDao.createTask(conn, title, dueDate, assignedTo, teamId);

            // 6) 커밋
            conn.commit();
            System.out.println("과제가 성공적으로 등록되었습니다.");
            return true;

        } catch (SQLException e) {
            // 예외 발생 시 롤백
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            System.err.println("과제 등록 중 오류 발생: " + e.getMessage());
            return false;
        
        } finally {
            // 커넥션 닫기
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }
}
