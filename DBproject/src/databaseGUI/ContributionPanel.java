package databaseGUI;

import database.dao.ContributionDao;
import database.model.Contribution;
import database.model.User;
import database.service.ContributionService;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContributionPanel extends JPanel {

    private MainAppGUI parent;
    private JLabel percentageLabel;
    private JProgressBar progressBar;

    public ContributionPanel(MainAppGUI parent) {
        this.parent = parent;
        setBackground(new Color(250, 250, 250));
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("📊 나의 기여도", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(250, 250, 250));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("SansSerif", Font.BOLD, 18));
        progressBar.setPreferredSize(new Dimension(400, 35));
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        percentageLabel = new JLabel("기여도: 0%", JLabel.CENTER);
        percentageLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        percentageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        percentageLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        centerPanel.add(progressBar);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(percentageLabel);
        add(centerPanel, BorderLayout.CENTER);

        JButton backBtn = new JButton("← 뒤로가기");
        backBtn.setBackground(new Color(255, 224, 178));
        backBtn.setFont(new Font("SansSerif", Font.PLAIN, 15));
        backBtn.setFocusPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(250, 250, 250));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        backBtn.addActionListener(e -> parent.showPanel("menu"));

        loadContributionData(parent.getUserId());
    }

    private void loadContributionData(int userId) {
        try {
            ContributionDao dao = new ContributionDao();
            List<Contribution> list = dao.getContributionsByUser(new User(userId));

            if (list == null || list.isEmpty()) {
                percentageLabel.setText("📬 기어도 데이터가 없습니다.");
                progressBar.setValue(0);
                return;
            }

            // 기여도 점수 계산용 임시 처리 (백엔드 분석 로직과 동일하게 구현)
            Contribution myContrib = list.get(0);
            int myUserId = myContrib.getUserId();
            int teamId = myContrib.getTeamId();

            List<Contribution> all = dao.findByTeamId(teamId);
            Map<Integer, Double> scoreMap = new HashMap<>();

            for (Contribution c : all) {
                double bonus = c.getBonus();
                double score = c.getSubmittedCount() + (c.getAttendedCount() * 0.5) + bonus;
                scoreMap.put(c.getUserId(), score);
            }

            double myScore = scoreMap.getOrDefault(myUserId, 0.0);
            double maxScore = scoreMap.values().stream().max(Double::compare).orElse(1.0);
            double percent = (myScore / maxScore) * 100;

            progressBar.setValue((int) percent);
            percentageLabel.setText(String.format("기어도: %.1f%%", percent));

        } catch (SQLException e) {
            percentageLabel.setText("❌ 오류 발생: " + e.getMessage());
            progressBar.setValue(0);
        }
    }
}
