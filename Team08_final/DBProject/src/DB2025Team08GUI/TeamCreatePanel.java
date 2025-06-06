package DB2025Team08GUI;

import DB2025Team08.dao.TeamDao;
import DB2025Team08.model.Team;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.time.LocalDate;

public class TeamCreatePanel extends JPanel {

    private MainAppGUI parent;

    public TeamCreatePanel(MainAppGUI parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("🆕 팀 만들기", JLabel.CENTER);
        title.setFont(new Font("Dialog", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JTextField nameField = new JTextField();
        JTextField subjectField = new JTextField();
        JTextField deadlineField = new JTextField("2025-06-30");

        formPanel.add(new JLabel("팀 이름:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("주제:"));
        formPanel.add(subjectField);
        formPanel.add(new JLabel("마감일 (YYYY-MM-DD):"));
        formPanel.add(deadlineField);

        JButton createBtn = new JButton("팀 생성");
        createBtn.addActionListener((ActionEvent e) -> {
            try (Connection conn = DB2025Team08.ConnectionManager.getConnection()) {
                conn.setAutoCommit(false);

                String name = nameField.getText().trim();
                String subject = subjectField.getText().trim();
                LocalDate deadline = LocalDate.parse(deadlineField.getText().trim());
                int userId = parent.getUserId();

                Team team = new Team(0, name, subject, deadline, userId);
                TeamDao dao = new TeamDao();

                int teamId = dao.createTeam(conn, team);
                if (teamId > 0) {
                    dao.addMember(conn, teamId, userId); // 팀장도 멤버로 추가
                    conn.commit();
                    JOptionPane.showMessageDialog(this, "✅ 팀이 성공적으로 생성되었습니다!");
                    parent.showMainMenuPanel();
                } else {
                    throw new Exception("팀 생성 실패");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "⚠️ 팀 생성 중 오류 발생!");
            }
        });

        add(formPanel, BorderLayout.CENTER);
        add(createBtn, BorderLayout.SOUTH);
    }
}
