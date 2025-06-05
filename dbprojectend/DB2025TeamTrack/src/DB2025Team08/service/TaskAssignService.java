package DB2025Team08.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import DB2025Team08.ConnectionManager;
import DB2025Team08.dao.TeamDao;
import DB2025Team08.dao.TaskDao;
import DB2025Team08.dto.MyPageDto;
import DB2025Team08.model.Team;
import DB2025Team08.model.User;

/**
 * 과제 할당 관련 서비스를 제공하는 클래스.
 */
public class TaskAssignService {
    /**
     * 과제 DAO 객체
     */
    private TaskDao taskDao;

    /**
     * 기본 생성자.
     */
    public TaskAssignService() {
        this.taskDao = new TaskDao();
    }
    
    /**
     * 팀에 과제를 부여한다.
     *
     * @param teamId       과제를 부여할 팀 ID
     * @param assignedTo   과제를 할당할 사용자 ID
     * @param title        과제 제목
     * @param dueDate      과제 마감일 (LocalDateTime)
     * @return 과제 등록 성공 시 true, 실패 시 false
     */
    public boolean assignTask(int teamId, int assignedTo, String title, LocalDateTime dueDate) {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Connection conn = null;

        try {
            // 트랜잭션 시작
            conn = ConnectionManager.getConnection();
            conn.setAutoCommit(false);

            // DAO에 Connection을 넘겨 과제 생성
            taskDao.createTask(conn, title, dueDate, assignedTo, teamId);

            // 커밋
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
