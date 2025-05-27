package database.dao;

import database.*;
import database.model.*;

import java.sql.*;
import java.time.*;
import java.util.*;

public class TeamDao {
	public TeamDao() {}
	
	public TeamDao(User user) {
		
	}
	
	public List<Team> getTeamsByUser(User user) throws SQLException{
		String sql = "SELECT t.team_id, t.team_name, t.subject, t.deadline, t.leader_id " +
                "FROM DB2025_TEAM t " +
                "JOIN DB2025_TEAM_MEMBER tm ON t.team_id = tm.team_id " +
                "WHERE tm.user_id = ?";
		try(Connection conn = ConnectionManager.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setInt(1, user.getUserId());
			try(ResultSet rs = ps.executeQuery()){
				List<Team> list = new ArrayList<>();
				while(rs.next()) {
					Team team = new Team(
						rs.getInt("team_id"),
						rs.getString("team_name"),
						rs.getString("subject"),
						rs.getTimestamp("deadline").toLocalDateTime(),
						rs.getInt("leader_id")
					);
					list.add(team);
				}
				return list;
			}
		}
	}
	public List<User> getTeamMembersByTeamId(int teamId) throws SQLException {
	    List<User> members = new ArrayList<>();

	    String sql = "SELECT u.user_id, u.name, u.email, ra.role_name " +
	                 "FROM DB2025_USER u " +
	                 "JOIN DB2025_TEAM_MEMBER tm ON u.user_id = tm.user_id " +
	                 "LEFT JOIN DB2025_ROLE_ASSIGNMENT ra ON u.user_id = ra.user_id AND tm.team_id = ra.team_id " +
	                 "WHERE tm.team_id = ?";

	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, teamId);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                User member = new User();
	                member.setUserId(rs.getInt("user_id"));
	                member.setName(rs.getString("name"));
	                member.setEmail(rs.getString("email"));
	                member.setRole(rs.getString("role_name"));

	                members.add(member);
	            }
	        }
	    }

	    return members;
	}


}