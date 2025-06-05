package DB2025Team08GUI;

import DB2025Team08.dao.MeetingDao;
import DB2025Team08.dto.MeetingDto;
import DB2025Team08.ConnectionManager;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.time.LocalDate;

public class MeetingRegisterPanel extends JPanel {

    private MainAppGUI parent;

    public MeetingRegisterPanel(MainAppGUI parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("📋 회의록 등록", JLabel.CENTER);
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(7, 1, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JTextField dateField = new JTextField("2025-06-04"); // 기본값: 오늘 날짜
        JTextArea contentArea = new JTextArea(4, 30);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        JScrollPane contentScroll = new JScrollPane(contentArea);

        JTextArea decisionArea = new JTextArea(3, 30);
        decisionArea.setLineWrap(true);
        decisionArea.setWrapStyleWord(true);
        JScrollPane decisionScroll = new JScrollPane(decisionArea);

        formPanel.add(new JLabel("📅 회의 날짜 (yyyy-MM-dd):"));
        formPanel.add(dateField);
        formPanel.add(new JLabel("📝 회의 내용:"));
        formPanel.add(contentScroll);
        formPanel.add(new JLabel("✅ 결정 사항:"));
        formPanel.add(decisionScroll);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton submitBtn = new JButton("등록");
        JButton cancelBtn = new JButton("취소");

        buttonPanel.add(submitBtn);
        buttonPanel.add(cancelBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // ✅ 등록 버튼 동작
        submitBtn.addActionListener(e -> {
            try {
                int userId = parent.getUserId();
                int teamId = parent.getCurrentTeamId();

                LocalDate meetingDate = LocalDate.parse(dateField.getText().trim());
                String content = contentArea.getText().trim();
                String decision = decisionArea.getText().trim();

                if (content.isEmpty() || decision.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "내용과 결정 사항을 모두 입력해주세요.");
                    return;
                }

                MeetingDto dto = new MeetingDto(teamId, meetingDate, content, decision);
                try (Connection conn = ConnectionManager.getConnection()) {
                    MeetingDao dao = new MeetingDao();
                    boolean success = dao.insertMeeting(conn, dto);

                    if (success) {
                        JOptionPane.showMessageDialog(this, "회의록이 등록되었습니다.");

                        // 🔥 teamId가 유지된 상태에서 회의 패널을 보여주고 새로고침도 보장
                        parent.showMeetingPanel(); // 내부에서 refreshMeetings() 이미 포함되어 있음
                    }
                    else {
                        JOptionPane.showMessageDialog(this, "등록에 실패했습니다.");
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "오류 발생: " + ex.getMessage());
                ex.printStackTrace();
            }
        });

        cancelBtn.addActionListener(e -> parent.showMeetingPanel());
    }
}
