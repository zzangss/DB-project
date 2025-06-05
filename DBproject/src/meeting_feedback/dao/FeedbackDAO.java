package meeting_feedback.dao;

import meeting_feedback.dto.FeedbackDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO {
    public boolean insertFeedback(Connection conn, FeedbackDTO feedback) throws SQLException {
        String sql = "INSERT INTO DB2025_feedback(sender_id, receiver_id, content) VALUES (?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, feedback.getSenderId());
            ps.setInt(2, feedback.getReceiverId());
            ps.setString(3, feedback.getContent());
            int affected = ps.executeUpdate();
            return affected > 0;
        }
    }

    public List<FeedbackDTO> getUserFeedbacks(Connection conn, int userId) throws SQLException {
        String sql = "SELECT sender_id, receiver_id, content FROM DB2025_feedback WHERE sender_id = ? OR receiver_id = ?";
        List<FeedbackDTO> feedbacks = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FeedbackDTO feedback = new FeedbackDTO(
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