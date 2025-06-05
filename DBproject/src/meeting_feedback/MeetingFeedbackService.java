package meeting_feedback;

import meeting_feedback.dao.MeetingDAO;
import meeting_feedback.dao.FeedbackDAO;
import meeting_feedback.dto.MeetingDTO;
import meeting_feedback.dto.FeedbackDTO;

import java.sql.Connection;
import java.sql.SQLException;
//import java.time.LocalDate;
import java.util.List;

public class MeetingFeedbackService {
    private final MeetingDAO meetingDAO = new MeetingDAO();
    private final FeedbackDAO feedbackDAO = new FeedbackDAO();

    public void sendFeedback(Connection conn, FeedbackDTO feedback) throws SQLException {
        boolean success = feedbackDAO.insertFeedback(conn, feedback);
        if (success) {
            System.out.println("피드백 전송 완료! (작성 시간:" + java.time.LocalDateTime.now() + ")");
        } else {
            System.out.println("피드백 전송 실패");
        }
    }

    public void writeMeeting(Connection conn, MeetingDTO meeting, int userId) throws SQLException {
        boolean prevAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);
        try {
            int leaderId = meetingDAO.getTeamLeaderId(conn, meeting.getTeamId());
            if (leaderId == -1) {
                System.out.println("팀이 존재하지 않습니다.");
                conn.rollback();
                return;
            }
            if (leaderId != userId) {
                System.out.println("권한이 없습니다. 팀장만 회의록을 작성할 수 있습니다.");
                conn.rollback();
                return;
            }
            boolean inserted = meetingDAO.insertMeeting(conn, meeting);
            if (inserted) {
                System.out.println("회의록 저장 완료.");
                conn.commit();
            } else {
                System.out.println("회의록 저장 실패.");
                conn.rollback();
            }
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(prevAutoCommit);
        }
    }

    public void viewMyFeedbacks(Connection conn, int userId) throws SQLException {
        List<FeedbackDTO> feedbacks = feedbackDAO.getUserFeedbacks(conn, userId);
        for (FeedbackDTO feedback : feedbacks) {
            System.out.printf("%d → %d : %s\n", feedback.getSenderId(), feedback.getReceiverId(), feedback.getContent());
        }
    }
}