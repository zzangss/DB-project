package DB2025Team08.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DB2025Team08.ConnectionManager;
import DB2025Team08.dto.FeedbackDto;

/**
 * 피드백 데이터를 처리하는 DAO 클래스.
 */
public class FeedbackDao {

    /**
     * 피드백을 데이터베이스에 삽입한다.
     *
     * @param conn 데이터베이스 연결 객체
     * @param feedback 삽입할 피드백 DTO
     * @return 삽입 성공 시 true, 실패 시 false
     * @throws SQLException SQL 실행 오류 발생 시
     */
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

    /**
     * 특정 사용자가 보낸 또는 받은 피드백 목록을 조회한다.
     *
     * @param userId 조회할 사용자 ID
     * @return 피드백 목록
     * @throws SQLException DB 조회 중 오류 발생 시
     */
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
