package meeting_feedback.dao;

import meeting_feedback.dto.MeetingDTO;
import java.sql.*;

public class MeetingDAO {
    public boolean insertMeeting(Connection conn, MeetingDTO meeting) throws SQLException {
        String sql = "INSERT INTO DB2025_meeting (team_id, meeting_date, content, decision) VALUES (?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, meeting.getTeamId());
            ps.setDate(2, Date.valueOf(meeting.getMeetingDate()));
            ps.setString(3, meeting.getContent());
            ps.setString(4, meeting.getDecision());
            int affected = ps.executeUpdate();
            return affected > 0;
        }
    }

    public int getTeamLeaderId(Connection conn, int teamId) throws SQLException {
        String sql = "SELECT leader_id FROM DB2025_team WHERE team_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teamId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("leader_id");
                } else {
                    return -1; // 팀 없음
                }
            }
        }
    }
}