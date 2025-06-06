package DB2025Team08GUI;

import javax.swing.*;

import DB2025Team08.dao.TeamDao;
import DB2025Team08.model.Team;
import DB2025Team08.model.User;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class MyPagePanel extends JPanel {
    private MainAppGUI parent;
    private int userId;

    public MyPagePanel(MainAppGUI parent, int userId) {
        this.parent = parent;
        this.userId = userId;
        setLayout(new BorderLayout());  // ✅ BorderLayout 명시 (오류 방지)
        setBackground(new Color(250, 250, 250));

        initializeUI();
    }

    private void initializeUI() {
        // 제목 라벨
        JLabel titleLabel = new JLabel("📄 나의 정보", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // 사용자 정보 패널
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(250, 250, 250));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));

        try {
            TeamDao teamDao = new TeamDao();
            List<Team> teams = teamDao.getTeamsByUser(new User(userId));

            if (teams.isEmpty()) {
                infoPanel.add(createInfoLabel("⚠️ 소속된 팀이 없습니다."));
            } else {
                Team team = teams.get(0); // 첫 번째 팀 기준
                infoPanel.add(createInfoLabel("사용자 ID: " + userId));
                infoPanel.add(createInfoLabel("🧑‍🤝‍🧑 팀 이름: " + team.getTeamName()));
                infoPanel.add(createInfoLabel("📘 주제: " + team.getSubject()));
                infoPanel.add(createInfoLabel("📅 마감일: " + team.getDeadline()));
            }

        } catch (SQLException e) {
            infoPanel.add(createInfoLabel("❌ 오류 발생: " + e.getMessage()));
        }

        add(infoPanel, BorderLayout.CENTER);

        // 뒤로가기 버튼
        JButton backBtn = new JButton("⬅ 뒤로가기");
        backBtn.setFont(new Font("SansSerif", Font.PLAIN, 16));
        backBtn.setBackground(new Color(200, 230, 201));
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setFocusPainted(false);
        backBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(250, 250, 250));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0));
        btnPanel.add(backBtn);

        backBtn.addActionListener(e -> parent.showPanel("menu"));

        add(btnPanel, BorderLayout.SOUTH);
    }

    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 17));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        return label;
    }
}
