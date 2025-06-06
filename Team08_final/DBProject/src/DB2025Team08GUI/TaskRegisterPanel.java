package DB2025Team08GUI;

import DB2025Team08.ConnectionManager;
import DB2025Team08.dao.TaskDao;
import DB2025Team08.dao.TeamDao;
import DB2025Team08.model.Task;
import DB2025Team08.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class TaskRegisterPanel extends JPanel {

    private MainAppGUI parent;
    private JTextField titleField;
    private JComboBox<User> memberComboBox;
    private JSpinner dateSpinner;

    public TaskRegisterPanel(MainAppGUI parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("📌 과제 등록", JLabel.CENTER);
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        formPanel.add(new JLabel("과제명:"));
        titleField = new JTextField();
        formPanel.add(titleField);

        formPanel.add(new JLabel("마감일:"));
        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
        formPanel.add(dateSpinner);

        formPanel.add(new JLabel("담당자:"));
        memberComboBox = new JComboBox<>();
        formPanel.add(memberComboBox);

        JButton submitBtn = new JButton("등록하기");
        submitBtn.addActionListener(this::handleSubmit);
        formPanel.add(submitBtn);

        JButton backBtn = new JButton("뒤로가기");
        backBtn.addActionListener(e -> parent.showPanel("menu"));
        formPanel.add(backBtn);

        add(formPanel, BorderLayout.CENTER);
    }

    public void loadTeamMembers() {
        try {
            TeamDao dao = new TeamDao();
            int teamId = parent.getCurrentTeamId();
            List<User> members = dao.getTeamMembersByTeamId(teamId);

            memberComboBox.removeAllItems();
            for (User member : members) {
                memberComboBox.addItem(member);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "팀원 로딩 실패: " + e.getMessage());
        }
    }

    private void handleSubmit(ActionEvent e) {
        try (Connection conn = ConnectionManager.getConnection()) {
            String title = titleField.getText();
            LocalDate date = ((java.util.Date) dateSpinner.getValue()).toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
            int assignedTo = ((User) memberComboBox.getSelectedItem()).getUserId();
            int teamId = parent.getCurrentTeamId();

            if (title.isEmpty()) {
                JOptionPane.showMessageDialog(this, "과제명을 입력하세요.");
                return;
            }

            TaskDao dao = new TaskDao();
            dao.createTask(conn, title, date.atStartOfDay(), assignedTo, teamId);

            JOptionPane.showMessageDialog(this, "과제가 성공적으로 등록되었습니다!");
            parent.showPanel("menu");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "과제 등록 실패: " + ex.getMessage());
        }
    }
}
