package DB2025Team08GUI;

import javax.swing.*;

import DB2025Team08.ConnectionManager;
import DB2025Team08.dao.FeedbackDao;
import DB2025Team08.dto.FeedbackDto;

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
        FeedbackDao dao = new FeedbackDao();

        try {
            List<FeedbackDto> feedbackList = dao.getUserFeedbacks(userId);  // ✅ 메서드 정확히 일치

            for (FeedbackDto feedback : feedbackList) {
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
