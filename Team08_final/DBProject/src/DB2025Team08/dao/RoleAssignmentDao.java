package DB2025Team08.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import DB2025Team08.*;
import DB2025Team08.model.*;

/**
 * 역할 할당 데이터를 처리하는 DAO 클래스.
 */
public class RoleAssignmentDao {
	
    /**
     * 역할을 설정한다.
     *
     * @param conn DB 연결 객체
     * @param teamId 팀 ID
     * @param userId 사용자 ID
     * @param roleName 역할 이름
     * @throws SQLException SQL 실행 오류 시
     */
	public void setRole(Connection conn, int teamId, int userId, String roleName) throws SQLException {
	    String sql = "INSERT INTO DB2025_ROLE_ASSIGNMENT (team_id, user_id, role_name) VALUES (?, ?, ?) " +
	                 "ON DUPLICATE KEY UPDATE role_name = VALUES(role_name)";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, teamId);
	        ps.setInt(2, userId);
	        ps.setString(3, roleName);
	        ps.executeUpdate();
	    }
	}
	
    /**
     * 사용자와 팀으로 역할을 조회한다.
     *
     * @param user 사용자 객체
     * @param team 팀 객체
     * @return 역할 할당 목록
     * @throws SQLException DB 조회 중 오류 시
     */
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

    /**
     * 사용자로 역할 목록을 조회한다.
     *
     * @param user 사용자 객체
     * @return 역할 할당 목록
     * @throws SQLException DB 조회 중 오류 시
     */
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
	
    /**
     * 팀 ID로 역할 맵을 조회한다.
     *
     * @param teamId 팀 ID
     * @return 사용자 ID를 키로 하는 역할 할당 맵
     * @throws SQLException DB 조회 중 오류 시
     */
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
	
    /**
     * 역할을 삽입하거나 업데이트한다.
     *
     * @param roleAssignment 역할 할당 객체
     * @throws SQLException SQL 실행 오류 시
     */
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
