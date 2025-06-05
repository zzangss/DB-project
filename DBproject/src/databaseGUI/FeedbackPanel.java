package databaseGUI;

import meeting_feedback.dao.FeedbackDAO;
import meeting_feedback.dto.FeedbackDTO;
import database.ConnectionManager;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FeedbackPanel extends JPanel {

    private MainAppGUI parent;
    private DefaultListModel<String> feedbackModel;
    private JList<String> feedbackList;

    public FeedbackPanel(MainAppGUI parent) {
        this.parent = parent;
        setLayout(new BorderLayout());
        initializeUI();
        loadFeedbacks();
    }

    private void initializeUI() {
        JLabel titleLabel = new JLabel("📩 받은 피드백", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        feedbackModel = new DefaultListModel<>();
        feedbackList = new JList<>(feedbackModel);
        JScrollPane scrollPane = new JScrollPane(feedbackList);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadFeedbacks() {
        int userId = parent.getUserId();
        FeedbackDAO dao = new FeedbackDAO();

        try (Connection conn = ConnectionManager.getConnection()) {
            List<FeedbackDTO> feedbackList = dao.getUserFeedbacks(conn, userId);  // ✅ 메서드 정확히 일치

            for (FeedbackDTO feedback : feedbackList) {
                String display = String.format(
                    "보낸이 ID: %d → 받는이 ID: %d\n내용: %s",
                    feedback.getSenderId(),
                    feedback.getReceiverId(),
                    feedback.getContent()
                );
                feedbackModel.addElement(display);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "피드백 불러오기 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
