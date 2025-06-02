package meeting_feedback;

import meeting_feedback.dto.FeedbackDTO;
import meeting_feedback.dto.MeetingDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
	static final String JDBC_URL="jdbc:mysql://localhost:3306/DB2025Team08";
	static final String USER="DB2025Team08";
	static final String PASS="DB2025Team08";
    public static void main(String[] args) {
    	

        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASS)) {
            MeetingFeedbackService service = new MeetingFeedbackService();

            // 피드백 보내기 예시
            FeedbackDTO feedback = new FeedbackDTO(1, 2, "좋은 발표였습니다.");
            service.sendFeedback(conn, feedback);

            // 회의록 작성 예시 (팀장 ID 1)
            MeetingDTO meeting = new MeetingDTO(10, LocalDate.now(), "회의 내용", "결정 사항");
            service.writeMeeting(conn, meeting, 1);

            // 내 피드백 조회 (userId 1)
            service.viewMyFeedbacks(conn, 1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}