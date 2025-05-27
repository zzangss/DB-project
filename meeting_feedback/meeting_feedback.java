package meeting_feedback;


import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalDate;



public class meeting_feedback{
	
	public void sendFeedback(Connection conn, int senderId,int receiverId, String content) throws SQLException{
		String sql = "INSERT INTO DB2025_feedback(sender_id, receiver_id, content) VALUES (?,?,?)";
		try(PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setInt(1, senderId);
			ps.setInt(2,receiverId);
			ps.setString(3, content);
			ps.executeUpdate();
			System.out.println("피드백 전송 완료! (작성 시간:" + LocalDateTime.now()+")");
		}
	}
	
	public void writMeeting(Connection conn, int teamId, LocalDate date, String content, String decision) throws SQLException{
		String insertMeetingSQL = "INSERT INTO DB2025_meeting (team_id, meeting_date,content, decision) VALUES (?,?,?,?)";
		try(PreparedStatement ps = conn.prepareStatement(insertMeetingSQL)){
				ps.setInt(1, teamId);
				ps.setDate(2, Date.valueOf(date));
				ps.setString(3, content);
				ps.setString(4, decision);
				ps.executeUpdate();
				
				System.out.println("회의록 저장 완료.");
		}
	}
	
	//피드백 열람 권한 제한 
	public void viewMyFeedbacks(Connection conn, int userId)throws SQLException{
		String sql = "SELECT * FROM DB2025_feecback WHERE sender_id = ? OR receiver_id = ?";
		try(PreparedStatement ps = conn.prepareStatement(sql)){
			ps.setInt(1, userId);
			ps.setInt(2, userId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				System.out.printf("[%s] %d → %d: %s\n",
	                    rs.getTimestamp("created_at"),
	                    rs.getInt("sender_id"),
	                    rs.getInt("receiver_id"),
	                    rs.getString("content"));
			}
		}
	}
	
}