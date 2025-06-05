package DB2025Team08.service;

import java.sql.Connection;
import java.sql.SQLException;
//import java.time.LocalDate;
import java.util.List;

import DB2025Team08.dao.FeedbackDao;
import DB2025Team08.dao.MeetingDao;
import DB2025Team08.dto.FeedbackDto;
import DB2025Team08.dto.MeetingDto;

/**
 * 회의록 및 피드백 처리 서비스 클래스.
 */
public class MeetingFeedbackService {
    /**
     * 회의 데이터 접근 객체
     */
    private final MeetingDao meetingDAO = new MeetingDao();

    /**
     * 피드백 데이터 접근 객체
     */
    private final FeedbackDao feedbackDAO = new FeedbackDao();

    /**
     * 피드백을 전송한다.
     *
     * @param conn 데이터베이스 연결 객체
     * @param feedback 전송할 피드백 DTO
     * @throws SQLException SQL 실행 오류 시
     */
    public void sendFeedback(Connection conn, FeedbackDto feedback) throws SQLException {
        boolean success = feedbackDAO.insertFeedback(conn, feedback);
        if (success) {
            System.out.println("피드백 전송 완료! (작성 시간:" + java.time.LocalDateTime.now() + ")");
        } else {
            System.out.println("피드백 전송 실패");
        }
    }

    /**
     * 회의록을 작성하고 저장한다.
     *
     * @param conn 데이터베이스 연결 객체
     * @param meeting 저장할 회의 DTO
     * @param userId 요청한 사용자 ID
     * @throws SQLException SQL 실행 오류 시
     */
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

    /**
     * 사용자의 받은 및 보낸 피드백을 출력한다.
     *
     * @param userId 조회할 사용자 ID
     * @throws SQLException SQL 실행 오류 시
     */
    public void viewMyFeedbacks(int userId) throws SQLException {
        List<FeedbackDto> feedbacks = feedbackDAO.getUserFeedbacks(userId);
        for (FeedbackDto feedback : feedbacks) {
            System.out.printf("%d → %d : %s\n", feedback.getSenderId(), feedback.getReceiverId(), feedback.getContent());
        }
    }
}
