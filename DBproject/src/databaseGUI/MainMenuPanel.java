package databaseGUI;

import javax.swing.*;
import java.awt.*;
import database.model.Team;
import database.dao.TeamDao;
import database.model.User;
import java.util.List;

public class MainMenuPanel extends JPanel {

    private MainAppGUI parent;

    public MainMenuPanel(MainAppGUI parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("🐯 TeamTrack Menu", JLabel.CENTER);
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 100));

        buttonPanel.add(createSmallButton("👥 팀 구성원 보기", e -> parent.showTeamPanel()));
        buttonPanel.add(Box.createVerticalStrut(10));

        buttonPanel.add(createSmallButton("🙋 마이페이지", e -> parent.showMyPagePanel()));
        buttonPanel.add(Box.createVerticalStrut(10));

        // ✅ 팀 관리 버튼 추가
        buttonPanel.add(createSmallButton("🛠 팀 관리", e -> openTeamManagePanel()));
        buttonPanel.add(Box.createVerticalStrut(10));

        buttonPanel.add(createSmallButton("📝 피드백 보기", e -> parent.showFeedbackPanel()));
        buttonPanel.add(Box.createVerticalStrut(10));

        buttonPanel.add(createSmallButton("📊 기여도 보기", e -> parent.showContributionPanel()));
        buttonPanel.add(Box.createVerticalStrut(10));

        buttonPanel.add(createSmallButton("📋 과제 상태 보기", e -> parent.showTaskStatusPanel()));
        buttonPanel.add(Box.createVerticalStrut(10));

        buttonPanel.add(createSmallButton("🔒 로그아웃", e -> parent.showPanel("login")));

        add(buttonPanel, BorderLayout.CENTER);
    }

    private JButton createSmallButton(String text, java.awt.event.ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Dialog", Font.PLAIN, 14));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(listener);
        return button;
    }

    // ✅ 팀 관리 패널 열기
    private void openTeamManagePanel() {
        try {
            int userId = parent.getUserId();
            TeamDao dao = new TeamDao();
            List<Team> teamList = dao.getTeamsByUser(new User(userId));

            if (teamList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "소속된 팀이 없습니다.");
                return;
            }

            Team selectedTeam = (Team) JOptionPane.showInputDialog(
                this,
                "관리할 팀을 선택하세요",
                "팀 선택",
                JOptionPane.PLAIN_MESSAGE,
                null,
                teamList.toArray(),
                teamList.get(0)
            );

            if (selectedTeam != null) {
                parent.showTeamManagePanel(selectedTeam);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "팀 정보를 불러오는 중 오류 발생:\n" + e.getMessage());
        }
    }
}
