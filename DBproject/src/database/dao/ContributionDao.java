package database.dao;

import database.model.Contribution;
import database.ConnectionManager;
import database.model.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class ContributionDao {
	
	public List<Contribution> getContributionsByUser(User user) throws SQLException {
        String sql = "SELECT user_id, team_id, submitted_count, attended_count, bonus, percentage " +
                     "FROM DB2025_CONTRIBUTION WHERE user_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, user.getUserId());
            try (ResultSet rs = ps.executeQuery()) {
                List<Contribution> list = new ArrayList<>();
                while (rs.next()) {
                    Contribution c = new Contribution(
                        rs.getInt("user_id"),
                        rs.getInt("team_id"),
                        rs.getInt("submitted_count"),
                        rs.getInt("attended_count"),
                        rs.getInt("bonus"),
                        rs.getDouble("percentage")
                    );
                    list.add(c);
                }
                return list;
            }
        }
    }
	
	public List<Contribution> findByTeamId(int teamId) throws SQLException {
	    List<Contribution> list = new ArrayList<>();
	    String sql = "SELECT * FROM DB2025_CONTRIBUTION WHERE team_id = ?";

	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, teamId);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                Contribution c = new Contribution();
	                c.setTeamId(rs.getInt("team_id"));
	                c.setUserId(rs.getInt("user_id"));
	                c.setSubmittedCount(rs.getInt("submitted_tasks_on_time"));
	                c.setAttendedCount(rs.getInt("meeting_attendance"));
	                list.add(c);
	            }
	        }
	    }

	    return list;
	}

}