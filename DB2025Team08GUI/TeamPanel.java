package DB2025Team08GUI;

import javax.swing.*;

import DB2025Team08.dao.TeamDao;
import DB2025Team08.model.Team;
import DB2025Team08.model.User;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class TeamPanel extends JPanel {

    private MainAppGUI parent;
    private JPanel listPanel;

    public TeamPanel(MainAppGUI parent) {
        this.parent = parent;
        setBackground(new Color(250, 250, 250));
        setLayout(new BorderLayout());

        initializeUI();
    }

    private void initializeUI() {
        // 제목
        JLabel titleLabel = new JLabel("🧑‍🤝‍🧑 팀 구성원", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // 구성원 리스트 패널
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(new Color(250, 250, 250));
        listPanel.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 60));

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setBackground(new Color(250, 250, 250));
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        add(scrollPane, BorderLayout.CENTER);

        // 뒤로가기 버튼
        JButton backBtn = new JButton("뒤로가기");
        backBtn.setBackground(new Color(255, 224, 178));
        backBtn.setFont(new Font("SansSerif", Font.PLAIN, 15));
        backBtn.setFocusPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(250, 250, 250));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        backBtn.addActionListener(e -> parent.showPanel("menu"));
    }

    public void loadTeamMembers(int userId) {
        listPanel.removeAll();

        try {
            TeamDao teamDao = new TeamDao();
            List<Team> teams = teamDao.getTeamsByUser(new User(userId));

            if (teams == null || teams.isEmpty()) {
                listPanel.add(createMemberLabel("⚠️ 소속된 팀이 없습니다."));
                revalidate(); repaint();
                return;
            }

            int teamId = teams.get(0).getTeamId();
            List<User> members = teamDao.getTeamMembersByTeamId(teamId);

            if (members == null || members.isEmpty()) {
                listPanel.add(createMemberLabel("😢 팀 구성원을 불러올 수 없습니다."));
                revalidate(); repaint();
                return;
            }

            for (User member : members) {
                boolean isMe = member.getUserId() == userId;
                boolean isLeader = teamDao.isLeader(teamId, member.getUserId());

                String role = (member.getRole() != null) ? member.getRole() : "역할 미정";

                StringBuilder display = new StringBuilder("👤 ");
                display.append(member.getName());

                if (isMe) display.append(" ⭐");
                display.append("  |  ").append(role);
                if (isLeader) display.append(" 🏆");

                listPanel.add(createMemberLabel(display.toString()));
            }

        } catch (SQLException e) {
            listPanel.add(createMemberLabel("❌ 오류: " + e.getMessage()));
        }

        revalidate();
        repaint();
    }

    private JLabel createMemberLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 17));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        return label;
    }
}
