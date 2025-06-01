package dao;

import domain.Task;

import java.sql.*;
//import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {
    private final Connection conn;

    public TaskDAO(Connection conn) {
        this.conn = conn;
    }

    // 내일 마감 과제 조회
    public List<Task> getTasksDueTomorrow() throws SQLException {
        List<Task> list = new ArrayList<>();
        String sql = "SELECT task_id, title, status, due_date FROM DB2025_task WHERE DATEDIFF(due_date, CURDATE()) = 1";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Task task = new Task(
                    rs.getInt("task_id"),
                    rs.getString("title"),
                    rs.getString("status"),
                    rs.getDate("due_date").toLocalDate()
                );
                list.add(task);
            }
        }
        return list;
    }

    // 전체 과제 조회
    public List<Task> getAllTasks() throws SQLException {
        List<Task> list = new ArrayList<>();
        String sql = "SELECT task_id, title, status, due_date FROM DB2025_task";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Task task = new Task(
                    rs.getInt("task_id"),
                    rs.getString("title"),
                    rs.getString("status"),
                    rs.getDate("due_date").toLocalDate()
                );
                list.add(task);
            }
        }
        return list;
    }

    // 상태 업데이트
    public void updateTaskStatus(int taskId, String newStatus) throws SQLException {
        String sql = "UPDATE DB2025_task SET status = ? WHERE task_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setInt(2, taskId);
            ps.executeUpdate();
        }
    }
}