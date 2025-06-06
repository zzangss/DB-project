package DB2025Team08.service;

import java.sql.Connection;
import java.sql.SQLException;
//import java.time.LocalDate;
import java.util.List;

import DB2025Team08.dao.FeedbackDao;
import DB2025Team08.dao.MeetingDao;
import DB2025Team08.dto.FeedbackDto;
import DB2025Team08.dto.MeetingDto;

public class MeetingFeedbackService {
    private final MeetingDao meetingDAO = new MeetingDao();
    private final FeedbackDao feedbackDAO = new FeedbackDao();

    public void sendFeedback(Connection conn, FeedbackDto feedback) throws SQLException {
        boolean success = feedbackDAO.insertFeedback(conn, feedback);
        if (success) {
            System.out.println("피드백 전송 완료! (작성 시간:" + java.time.LocalDateTime.now() + ")");
        } else {
            System.out.println("피드백 전송 실패");
        }
    }

    public void writeMeeting(Connection conn, MeetingDto meeting, int userId) throws SQLException {
        boolean prevAutoCommit = conn.getAutoCommit();
        conn.setAutoCommit(false);
        try {
            int leaderId = meetingDAO.getTeamLeaderId(meeting.getTeamId());
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

    public void viewMyFeedbacks(int userId) throws SQLException {
        List<FeedbackDto> feedbacks = feedbackDAO.getUserFeedbacks(userId);
        for (FeedbackDto feedback : feedbacks) {
            System.out.printf("%d → %d : %s\n", feedback.getSenderId(), feedback.getReceiverId(), feedback.getContent());
        }
    }
}