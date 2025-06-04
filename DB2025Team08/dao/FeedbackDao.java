package DB2025Team08.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DB2025Team08.ConnectionManager;
import DB2025Team08.dto.FeedbackDto;

public class FeedbackDao {
    public boolean insertFeedback(Connection conn, FeedbackDto feedback) throws SQLException {
        String sql = "INSERT INTO DB2025_feedback(sender_id, receiver_id, content) VALUES (?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, feedback.getSenderId());
            ps.setInt(2, feedback.getReceiverId());
            ps.setString(3, feedback.getContent());
            int affected = ps.executeUpdate();
            return affected > 0;
        }
    }

    public List<FeedbackDto> getUserFeedbacks(int userId) throws SQLException {
        String sql = "SELECT sender_id, receiver_id, content FROM DB2025_feedback WHERE sender_id = ? OR receiver_id = ?";
        List<FeedbackDto> feedbacks = new ArrayList<>();
        try (Connection conn = ConnectionManager.getConnection();
        		PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FeedbackDto feedback = new FeedbackDto(
                        rs.getInt("sender_id"),
                        rs.getInt("receiver_id"),
                        rs.getString("content"));
                    feedbacks.add(feedback);
                }
            }
        }
        return feedbacks;
    }
}