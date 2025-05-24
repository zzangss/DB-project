package databaseGUI;

import javax.swing.*;        // JFrame, JPanel, JButton, JTextField, JPasswordField 등
import java.awt.*;           // Layout 관련 (GridLayout 등)
import java.awt.event.*;     // 버튼 클릭 이벤트 리스너
import database.dao.UserDao;
import database.model.User;
//로그인 화면 구성, 사용자 입력을 받아 로그임 시도 -> 성공하면 다른 화면으로 전환

public class LoginPanel extends JPanel {
    public LoginPanel(MainAppGUI app) {
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 245, 245));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ✅ 제목 추가
        JLabel titleLabel = new JLabel("DB 프로젝트 - TeamTrack", JLabel.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        titleLabel.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;  // 가로 두 칸 병합
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        // ✅ 나머지 레이블/필드
        gbc.gridwidth = 1; // 다시 1로 초기화
        gbc.anchor = GridBagConstraints.WEST;

        JLabel idLabel = new JLabel("이메일:");
        idLabel.setForeground(Color.DARK_GRAY);
        JLabel pwLabel = new JLabel("비밀번호:");
        pwLabel.setForeground(Color.DARK_GRAY);

        JTextField idField = new JTextField(15);
        JPasswordField pwField = new JPasswordField(15);
        JButton loginBtn = new JButton("로그인");
        loginBtn.setBackground(new Color(255, 223, 186));
        JButton registerBtn = new JButton("회원가입");
        registerBtn.setBackground(new Color(186, 225, 255));
        registerBtn.setForeground(Color.BLACK);

        gbc.gridx = 0; gbc.gridy = 1;
        add(idLabel, gbc);
        gbc.gridx = 1;
        add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        add(pwLabel, gbc);
        gbc.gridx = 1;
        add(pwField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        add(loginBtn, gbc);
        gbc.gridx = 1;
        add(registerBtn, gbc);

        loginBtn.addActionListener(e -> {
            String email = idField.getText();
            String password = new String(pwField.getPassword());
            
            try {
            	UserDao userDao = new UserDao();
            	User user = userDao.findByEmailAndPassword(email, password);
            	if (user != null) {
                    JOptionPane.showMessageDialog(this, "로그인 성공! 어서오세요, " + user.getName() + "님");
                    app.showPanel("menu");
                } else {
                    JOptionPane.showMessageDialog(this, "로그인 실패: 이메일 또는 비밀번호가 틀렸습니다.");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "로그인 중 오류 발생: " + ex.getMessage());
            }
        });
    }
}

