package DB2025Team08.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import DB2025Team08.*;
import DB2025Team08.model.*;


public class TaskDao {
	//user의 과제를 list로 반환
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
	 //트랜잭션 제어를 위한 conn
	 //과제의 이름, 마감 기한, 할당받은 유저id, 소속된 팀id 로 과제 생성
	 public void createTask(Connection conn, String title, LocalDateTime dueDate, int assignedTo, int teamId) throws SQLException {
		    String sql = "INSERT INTO DB2025_TASK (title, due_date, assigned_to, team_id) VALUES (?, ?, ?, ?)";
		    try (PreparedStatement ps = conn.prepareStatement(sql)) {
		        ps.setString(1, title);
		        ps.setTimestamp(2, Timestamp.valueOf(dueDate));
		        ps.setInt(3, assignedTo);
		        ps.setInt(4, teamId);
		        ps.executeUpdate();
		    }
		}
}
