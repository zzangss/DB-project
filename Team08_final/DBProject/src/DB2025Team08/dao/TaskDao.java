package DB2025Team08.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import DB2025Team08.*;
import DB2025Team08.model.*;

/**
 * 과제 관련 데이터를 처리하는 DAO 클래스.
 */
public class TaskDao {

    /**
     * 사용자가 할당받은 과제 목록을 조회한다.
     *
     * @param user 조회할 사용자 객체
     * @return 사용자가 할당받은 과제 목록
     * @throws SQLException DB 조회 중 오류 발생 시
     */
    public List<Task> getTasksByUser(User user) throws SQLException {
        String sql = "SELECT task_id, title, due_date, status, assigned_to, team_id " +
                     "FROM DB2025_TASK WHERE assigned_to = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, user.getUserId());
            try (ResultSet rs = ps.executeQuery()) {
                List<Task> list = new ArrayList<>();
                while (rs.next()) {
                    Task t = new Task(
                        rs.getInt("task_id"),
                        rs.getString("title"),
                        rs.getTimestamp("due_date").toLocalDateTime().toLocalDate(),
                        rs.getString("status"),
                        rs.getInt("assigned_to"),
                        rs.getInt("team_id")
                    );
                    list.add(t);
                }
                return list;
            }
        }
    }

    /**
     * 새로운 과제를 생성한다.
     *
     * @param conn DB 연결 객체 (트랜잭션 제어용)
     * @param title 과제 제목
     * @param dueDate 마감 기한
     * @param assignedTo 할당받은 사용자 ID
     * @param teamId 소속 팀 ID
     * @throws SQLException 과제 생성 중 오류 발생 시
     */
    public void createTask(Connection conn, String title,
                           LocalDateTime dueDate, int assignedTo, int teamId) throws SQLException {
        String sql = "INSERT INTO DB2025_TASK (title, due_date, assigned_to, team_id) "
                   + "VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, title);
            ps.setTimestamp(2, Timestamp.valueOf(dueDate));
            ps.setInt(3, assignedTo);
            ps.setInt(4, teamId);
            ps.executeUpdate();
        }
    }

    /**
     * 과제 상태를 업데이트한다.
     * <ul>
     *   <li>현재 상태가 "미제출"이고, 새 상태가 "진행 중"일 때만 허용</li>
     * </ul>
     *
     * @param conn DB 연결 객체 (트랜잭션 제어용)
     * @param taskId 상태를 변경할 과제 ID
     * @param userId 요청한 사용자 ID
     * @param currentStatus 현재 상태
     * @param newStatus 변경할 상태
     * @return 상태 변경 성공 시 true, 실패 시 false
     * @throws SQLException 상태 업데이트 중 오류 발생 시
     */
    public boolean updateTaskStatus(Connection conn, int taskId, int userId, String currentStatus, String newStatus) throws SQLException {
        // 현재 상태가 "미제출"이 아니면 변경 불가
        if (!"미제출".equals(currentStatus)) {
            return false;
        }
        // 새 상태가 "진행 중"이 아니면 변경 불가
        if (!"진행 중".equals(newStatus)) {
            return false;
        }

        String updateSql = "UPDATE DB2025_TASK SET status = ? WHERE task_id = ?";
        try (PreparedStatement psUpdate = conn.prepareStatement(updateSql)) {
            psUpdate.setString(1, newStatus);
            psUpdate.setInt(2, taskId);
            int affectedRows = psUpdate.executeUpdate();
            return (affectedRows > 0);
        }
    }

}
