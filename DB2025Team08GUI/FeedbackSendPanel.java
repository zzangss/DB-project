package DB2025Team08GUI;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

import DB2025Team08.ConnectionManager;
import DB2025Team08.dao.FeedbackDao;
import DB2025Team08.dao.TeamDao;
import DB2025Team08.dto.FeedbackDto;
import DB2025Team08.model.User;

public class FeedbackSendPanel extends JPanel {
    private MainAppGUI parent;
    private JComboBox<User> receiverComboBox;
    private JTextArea contentArea;

    public FeedbackSendPanel(MainAppGUI parent) {
        this.parent = parent;
        setLayout(new BorderLayout());
        initializeUI();
    }

    private void initializeUI() {
        JLabel titleLabel = new JLabel("✉ 피드백 보내기", JLabel.CENTER);
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        receiverComboBox = new JComboBox<>();
        JPanel receiverPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        receiverPanel.add(new JLabel("수신자: "));
        receiverPanel.add(receiverComboBox);

        centerPanel.add(receiverPanel, BorderLayout.NORTH);

        contentArea = new JTextArea(10, 40);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        JScrollPane contentScroll = new JScrollPane(contentArea);

        centerPanel.add(new JLabel("내용 입력:"), BorderLayout.CENTER);
        centerPanel.add(contentScroll, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        JButton sendBtn = new JButton("📨 전송");
        sendBtn.addActionListener(e -> sendFeedback());

        JButton backBtn = new JButton("⬅ 돌아가기");
        backBtn.addActionListener(e -> parent.showPanel("menu"));

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(sendBtn);
        bottomPanel.add(backBtn);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void loadTeamMembers() {
        try {
            TeamDao dao = new TeamDao();
            int teamId = parent.getCurrentTeamId();
            List<User> members = dao.getTeamMembersByTeamId(teamId);

            receiverComboBox.removeAllItems();
            int userId = parent.getUserId();

            for (User member : members) {
                if (member.getUserId() != userId) {
                    receiverComboBox.addItem(member); // User에 toString() 오버라이드 필요
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "팀원 불러오기 실패: " + e.getMessage());
        }
    }

    private void sendFeedback() {
        User receiver = (User) receiverComboBox.getSelectedItem();
        String content = contentArea.getText().trim();

        if (receiver == null || content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "수신자와 내용을 모두 입력해주세요.");
            return;
        }

        try (Connection conn = ConnectionManager.getConnection()) {
            FeedbackDao dao = new FeedbackDao();
            FeedbackDto dto = new FeedbackDto(parent.getUserId(), receiver.getUserId(), content);
            dao.insertFeedback(conn, dto);
            JOptionPane.showMessageDialog(this, "피드백 전송 완료!");
            contentArea.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "전송 실패: " + e.getMessage());
        }
    }
}
