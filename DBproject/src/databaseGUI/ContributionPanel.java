package databaseGUI;

//db2025_view_contributon_summary 뷰를 활용해서 기여도 데이터를 리스트나 표로 보여주는 화면을 만들기

import javax.swing.*;
import java.awt.*;

public class ContributionPanel extends JPanel {
    public ContributionPanel(MainAppGUI app) {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("📊 팀원 기여도 보기", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // 📋 테이블 데이터 준비 (샘플)
        String[] columns = { "이름", "제출횟수", "출석횟수", "보너스", "기여율(%)" };
        Object[][] data = {
            {"원빈", 1, 2, 1.0, 100.0},
            {"은석", 0, 1, 0.0, 45.0},
            {"성찬", 0, 1, 0.0, 45.0},
            {"쇼타로", 1, 2, 1.0, 100.0},
            {"소희", 2, 2, 1.0, 100.0},
            {"앤톤", 1, 2, 1.0, 100.0},
        };

        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 뒤로가기 버튼
        JButton backBtn = new JButton("뒤로가기");
        add(backBtn, BorderLayout.SOUTH);

        backBtn.addActionListener(e -> {
            app.showPanel("team");  // 또는 다른 화면으로 이동
        });
    }
}