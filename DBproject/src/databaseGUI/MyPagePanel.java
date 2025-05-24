package databaseGUI;

import javax.swing.*;
import java.awt.*;
import database.model.User;
import database.dao.UserDao;

public class MyPagePanel extends JPanel {
    private JLabel nameLabel;
    private JLabel emailLabel;
    private JLabel roleLabel;
    private JLabel dateLabel;

    public MyPagePanel(MainAppGUI app, int userId) {
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);

        JLabel title = new JLabel("🙋‍♀️ 내 정보");
        title.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        title.setForeground(new Color(40, 40, 40));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        // 라벨 초기화
        nameLabel = new JLabel();
        emailLabel = new JLabel();
        roleLabel = new JLabel();
        dateLabel = new JLabel();

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 1; add(new JLabel("이름:"), gbc);
        gbc.gridx = 1; add(nameLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(new JLabel("이메일:"), gbc);
        gbc.gridx = 1; add(emailLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 3; add(new JLabel("권한:"), gbc);
        gbc.gridx = 1; add(roleLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 4; add(new JLabel("가입일:"), gbc);
        gbc.gridx = 1; add(dateLabel, gbc);

        JButton backBtn = new JButton("뒤로가기");
        backBtn.setBackground(new Color(255, 223, 186));
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(backBtn, gbc);

        backBtn.addActionListener(e -> {
            app.showPanel("menu");
        });

        // 🟡 실제 DB에서 사용자 정보 불러오기
        try {
            UserDao dao = new UserDao();
            User user = dao.findById(userId);
            if (user != null) {
                nameLabel.setText(user.getName());
                emailLabel.setText(user.getEmail());
                roleLabel.setText(user.getRole());
                dateLabel.setText(user.getCreatedAt().toLocalDate().toString());
            } else {
                JOptionPane.showMessageDialog(this, "유저를 찾을 수 없습니다.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "오류 발생: " + ex.getMessage());
        }
    }
}
