package database.dao;

import database.*;
import database.model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;


public class TaskDao {
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
	                        rs.getTimestamp("due_date").toLocalDateTime(),
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
	 public void createTask(Task task) throws SQLException {
		    String sql = "INSERT INTO DB2025_TASK (task_id, title, due_date, status, assigned_to, team_id) VALUES (?, ?, ?, ?, ? ,?)";
		    try (Connection conn = ConnectionManager.getConnection();
		         PreparedStatement ps = conn.prepareStatement(sql)) {
		        ps.setInt(1, task.getTaskId());
		        ps.setString(2, task.getTitle());
		        ps.setTimestamp(3, Timestamp.valueOf(task.getDueDate()));
		        ps.setString(4, task.getStatus());
		        ps.setInt(5, task.getAssignedTo());
		        ps.setInt(6, task.getTeamId());
		        ps.executeUpdate();
		    }
		}

}
