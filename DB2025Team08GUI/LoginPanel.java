package DB2025Team08GUI;

import javax.swing.*;
import DB2025Team08.dao.UserDao;
import DB2025Team08.dao.TeamDao;             // ✅ 추가
import DB2025Team08.model.User;
import DB2025Team08.model.Team;             // ✅ 추가

import java.awt.*;
import java.util.List;                     // ✅ 추가

public class LoginPanel extends JPanel {
    public LoginPanel(MainAppGUI app) {
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("DB 프로젝트 - TeamTrack", JLabel.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        titleLabel.setForeground(new Color(50, 50, 50));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        gbc.gridwidth = 1;
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
            String email = idField.getText().trim();
            String password = new String(pwField.getPassword());

            try {
                UserDao userDao = new UserDao();
                User user = userDao.findByEmailAndPassword(email, password);

                if (user != null) {
                    app.setUserId(user.getUserId());

                    TeamDao teamDao = new TeamDao();
                    List<Team> teams = teamDao.getTeamsByUser(user);

                    if (!teams.isEmpty()) {
                        app.setCurrentTeamId(teams.get(0).getTeamId());
                    } else {
                        app.setCurrentTeamId(0); // 또는 -1 등, 팀 없는 상태를 의미하는 값
                    }

                    JOptionPane.showMessageDialog(this, "로그인 성공! 어서오세요, " + user.getName() + "님");
                    app.showPanel("menu");  // ✅ 무조건 메인메뉴로 이동

                } else {
                    JOptionPane.showMessageDialog(this, "로그인 실패: 이메일 또는 비밀번호가 틀렸습니다.");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "로그인 중 오류 발생: " + ex.getMessage());
            }
        });



        registerBtn.addActionListener(e -> {
            app.showPanel("register");
        });
    }
}