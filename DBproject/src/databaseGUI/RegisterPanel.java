package databaseGUI;

import javax.swing.*;
import java.awt.*;
import database.model.User;
import database.dao.UserDao;

public class RegisterPanel extends JPanel {
    public RegisterPanel(MainAppGUI app) {
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 🔹 제목
        JLabel titleLabel = new JLabel("👤 회원가입", JLabel.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        titleLabel.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        // 🔸 입력 필드
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel nameLabel = new JLabel("이름:");
        nameLabel.setForeground(Color.DARK_GRAY);
        JTextField nameField = new JTextField(15);

        JLabel emailLabel = new JLabel("이메일:");
        emailLabel.setForeground(Color.DARK_GRAY);
        JTextField emailField = new JTextField(15);

        JLabel pwLabel = new JLabel("비밀번호:");
        pwLabel.setForeground(Color.DARK_GRAY);
        JPasswordField pwField = new JPasswordField(15);

        // 🔸 버튼
        JButton registerBtn = new JButton("가입하기");
        registerBtn.setBackground(new Color(186, 225, 255));
        registerBtn.setForeground(Color.BLACK);

        JButton backBtn = new JButton("뒤로가기");
        backBtn.setBackground(new Color(255, 223, 186));
        backBtn.setForeground(Color.BLACK);

        // 🔹 컴포넌트 배치
        gbc.gridx = 0; gbc.gridy = 1; add(nameLabel, gbc);
        gbc.gridx = 1; add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; add(emailLabel, gbc);
        gbc.gridx = 1; add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; add(pwLabel, gbc);
        gbc.gridx = 1; add(pwField, gbc);

        gbc.gridx = 0; gbc.gridy = 4; add(registerBtn, gbc);
        gbc.gridx = 1; add(backBtn, gbc);

        // 🔹 이벤트 처리
        registerBtn.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(pwField.getPassword());

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "모든 필드를 입력해주세요.");
                return;
            }

            try {
                User user = new User();
                user.setName(name);
                user.setEmail(email);
                user.setPassword(password);
                user.setRole("student");  // 기본 권한

                UserDao dao = new UserDao();
                dao.create(user);

                JOptionPane.showMessageDialog(this, "회원가입 성공!");
                app.showPanel("login");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "회원가입 실패: " + ex.getMessage());
            }
        });

        backBtn.addActionListener(e -> {
            app.showPanel("login");
        });
    }
}
