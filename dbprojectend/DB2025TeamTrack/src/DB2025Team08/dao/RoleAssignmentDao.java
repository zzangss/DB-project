package DB2025Team08.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import DB2025Team08.*;
import DB2025Team08.model.*;

public class RoleAssignmentDao{
	
	//트랜잭션을 위한 conn
	//팀id, 유저id, 역할 이름을 매개변수로.
	public void setRole(Connection conn, int teamId, int userId, String roleName) throws SQLException {
	    String sql = "INSERT INTO DB2025_ROLE_ASSIGNMENT (team_id, user_id, role_name) VALUES (?, ?, ?) " +
	                 "ON DUPLICATE KEY UPDATE role_name = VALUES(role_name)"; // 역할 중복 시 업데이트

	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, teamId);
	        ps.setInt(2, userId);
	        ps.setString(3, roleName);
	        ps.executeUpdate();
	    }
	}
	
	public List<RoleAssignment> getRoleByUserAndTeam(User user, Team team) throws SQLException  {
		String sql = 	"select r.team_id, r.user_id, role_name" +
						"from DB2025_ROLE_ASSIGNMENT" +
						"where team_id = ? And user_id = ?";
		
		try(Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql)){
			try(ResultSet rs = ps.executeQuery()){
				List<RoleAssignment> list = new ArrayList();
				while(rs.next()) {
					RoleAssignment r = new RoleAssignment(
							rs.getInt("team_id"),
							rs.getInt("user_id"),
							rs.getString("role_name")
					);
					list.add(r);
				}
				return list;
			}
		}
	}

	public List<RoleAssignment> getRolesByUser(User user) throws SQLException {
		String sql = 	"select team_id, user_id, role_name " +
				"from DB2025_role_assignment " +
				"where user_id = ?";

		try(Connection conn = ConnectionManager.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setInt(1, user.getUserId());
			try(ResultSet rs = ps.executeQuery()){
				List<RoleAssignment> list = new ArrayList<RoleAssignment>();
				while(rs.next()) {
					RoleAssignment r = new RoleAssignment(
							rs.getInt("team_id"),
							rs.getInt("user_id"),
							rs.getString("role_name")
					);
					list.add(r);
				}
				return list;
			}
		}
	}
	
	public Map<Integer, RoleAssignment> findRolesByTeamId(int teamId) throws SQLException {
	    Map<Integer, RoleAssignment> map = new HashMap<>();
	    String sql = "SELECT * FROM DB2025_ROLE_ASSIGNMENT WHERE team_id = ?";

	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, teamId);
	        try (ResultSet rs = ps.executeQuery()) {
	            while (rs.next()) {
	                RoleAssignment ra = new RoleAssignment();
	                ra.setTeamId(rs.getInt("team_id"));
	                ra.setUserId(rs.getInt("user_id"));
	                ra.setRoleName(rs.getString("role_name"));
	                map.put(ra.getUserId(), ra);
	            }
	        }
	    }

	    return map;
	}
	
	public void assignRole(RoleAssignment roleAssignment) throws SQLException {
	    String sql = "REPLACE INTO DB2025_ROLE_ASSIGNMENT (team_id, user_id, role_name) VALUES (?, ?, ?)";
	    try (Connection conn = ConnectionManager.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, roleAssignment.getTeamId());
	        ps.setInt(2, roleAssignment.getUserId());
	        ps.setString(3, roleAssignment.getRoleName());
	        ps.executeUpdate();
	    }
	}


}