package databaseGUI;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {
    public MainMenuPanel(MainAppGUI app) {
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);

        JLabel title = new JLabel("🌟 어떤 정보를 보고 싶으신가요?");
        title.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        title.setForeground(new Color(40, 40, 40));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1;

        JButton myPageBtn = new JButton("🙋‍♀️ 내 정보 보기");
        myPageBtn.setBackground(new Color(255, 223, 186));
        JButton teamBtn = new JButton("👥 팀 구성원 보기");
        teamBtn.setBackground(new Color(186, 225, 255));

        gbc.gridx = 0; gbc.gridy = 1; add(myPageBtn, gbc);
        gbc.gridx = 1; add(teamBtn, gbc);

        // 버튼 클릭 시 해당 패널로 이동
        myPageBtn.addActionListener(e -> app.showPanel("mypage"));
        teamBtn.addActionListener(e -> app.showPanel("team"));
    }
}
