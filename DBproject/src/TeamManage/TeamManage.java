package TeamManage;

import java.sql.*;
import java.time.LocalDate;

public class TeamManage{
	public boolean delegateAndLeave(Connection conn, int teamId, int LeaderId, int newLeaderId)throws SQLException{
		String checksql = "SELECT leader_id FROM DB2025_team WHERE team_id = ?";
		try(PreparedStatement ps = conn.prepareStatement(checksql)){
			ps.setInt(1, teamId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int leaderId = rs.getInt("leader_id");
				if(leaderId != LeaderId) {
					System.out.println("현 사용자는 팀장이 아닙니다.");
					return false;
				}
				}else {
					System.out.println("존재하지 않는 팀입니다.");
					return false;
				}
			}
		
		String memberCheck = "SELECT * FROM DB2025_team_member WHERE team_id = ? AND user_id=? ";
	    try(PreparedStatement ps = conn.prepareStatement(memberCheck)){
	    	ps.setInt(1, teamId);
	    	ps.setInt(2, newLeaderId);
	    	ResultSet rs = ps.executeQuery();
	    	if(!rs.next()) {
	    		System.out.println("위임대상이 멤버가 아닙니다");
	    		return false;
	    	}
	    }
	    
	    String updatesql = "UPDATE DB2025_team SET leader_id = ? WHERE team_id = ?";
	    try(PreparedStatement ps = conn.prepareStatement(updatesql)){
	    	ps.setInt(1,newLeaderId);
	    	ps.setInt(2, teamId);
	    	ps.executeUpdate();
	    }
			
		return leaveTeam(conn, teamId, LeaderId);
	}
	
	public boolean leaveTeam(Connection conn, int teamId, int userId)throws SQLException{
		String deadlinesql = "SELECT deadline, leader_id FROM DB2025_team WHERE team_id=?";
		Date deadline;
		int leaderId;
		try(PreparedStatement ps = conn.prepareStatement(deadlinesql)){
			ps.setInt(1,teamId);
			ResultSet rs = ps.executeQuery();
			if(!rs.next()) {
				System.out.println("팀이 존재하지 않습니다.");
				return false;
			}
			deadline = rs.getDate("deadline");
			leaderId = rs.getInt("leader_id");
		}
		
		boolean isLeader = (leaderId == userId);
		LocalDate today = LocalDate.now();
		boolean isAfterDeadline = today.isAfter(deadline.toLocalDate());
		
		if(!isLeader && !isAfterDeadline) {
			System.out.println("팀원은 마감일 이전에 탈퇴할 수 없습니다.");
			return false;
		}
		
		String deleteSql="DELETE FROM DB2025_team_member WHERE team_id = ? AND user_id =?";
		try(PreparedStatement ps = conn.prepareStatement(deleteSql)){
			ps.setInt(1, teamId);
			ps.setInt(2, userId);
			int affected = ps.executeUpdate();
			if(affected > 0) {
				System.out.println("팀 탈퇴 완료");
				return true;
			}else {
				System.out.println("해당 유저는 이 팀의 팀원이 아닙니다.");
				return false;
			}
			
		}
		}
	
	public int createTeam(Connection conn, String teamName, String subject, LocalDate deadline, int leaderId)throws SQLException{
		String insertTeam = "INSERT INTO DB2025_team (team_name, subject, deadline, leader_id) VALUES (?,?,?,?)";
		try(PreparedStatement ps = conn.prepareStatement(insertTeam, Statement.RETURN_GENERATED_KEYS)){
			ps.setString(1, teamName);
			ps.setString(2, subject);
			ps.setDate(3, Date.valueOf(deadline));
			ps.setInt(4, leaderId);
			
			int affectedRows = ps.executeUpdate();
			if(affectedRows == 0) {
				System.out.println("팀 생성 실패");
				return -1;
			}
			
			try(ResultSet generatedKeys = ps.getGeneratedKeys()){
				if(generatedKeys.next()) {
					int teamId = generatedKeys.getInt(1);
					System.out.println("팀 생성 성공 (ID: " + teamId + ")");
					
					addTeamMember(conn, teamId, leaderId);
					return teamId;
				}else {
					throw new SQLException("팀 ID 생성 실패");
				}
			}
		}
	}
	public boolean inviteMember(Connection conn, int teamId, String email)throws SQLException{
		String findUser = "SELECT user_id FROM DB2025_user WHERE email = ?";
		try(PreparedStatement findStmt = conn.prepareStatement(findUser)){
			findStmt.setString(1, email);
			try(ResultSet rs = findStmt.executeQuery()){
				if(!rs.next()) {
					System.out.println("해당 이메일로 등록된 사용자가 없습니다.");
					return false;
				}
				int userId = rs.getInt("user_id");
				
				return addTeamMember(conn, teamId, userId);
				
				}
			}
		}
	
	public boolean addTeamMember(Connection conn, int teamId, int userId)throws SQLException{
		String checkduplication = "SELECT * FROM DB2025_team_member WHERE team_id = ? AND user_id=?";
		try(PreparedStatement checkStmt = conn.prepareStatement(checkduplication)){
			checkStmt.setInt(1, teamId);
			checkStmt.setInt(2, userId);
			ResultSet rs = checkStmt.executeQuery();
			if(rs.next()) {
				System.out.println("이미 팀에 속한 사용자입니다.");
				return false;
			}
		}
		
		String insertSql = "INSERT INTO DB2025_team_member (team_id, user_id) VALUES (?,?)";
		try(PreparedStatement ps = conn.prepareStatement(insertSql)){
			ps.setInt(1, teamId);
			ps.setInt(2, userId);
			ps.executeUpdate();
			System.out.println("팀원 초대 성공 (user_id:"+userId + ")");
			return true;
		}
	}
	 
	}