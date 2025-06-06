// DB2025Team08GUI.RoleAssignPanel.java

package DB2025Team08GUI;

import DB2025Team08.dao.RoleAssignmentDao;
import DB2025Team08.dao.TeamDao;
import DB2025Team08.model.User;
import DB2025Team08.model.RoleAssignment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class RoleAssignPanel extends JPanel {

    private MainAppGUI parent;
    private JComboBox<User> memberComboBox;
    private JTextField roleField;

    public RoleAssignPanel(MainAppGUI parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("🎖 역할 부여", JLabel.CENTER);
        title.setFont(new Font("Dialog", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        centerPanel.add(new JLabel("팀원:"));
        memberComboBox = new JComboBox<>();
        centerPanel.add(memberComboBox);

        centerPanel.add(new JLabel("역할 입력:"));
        roleField = new JTextField();
        centerPanel.add(roleField);

        JButton assignBtn = new JButton("역할 부여");
        assignBtn.addActionListener(this::handleAssign);
        centerPanel.add(assignBtn);

        JButton backBtn = new JButton("뒤로가기");
        backBtn.addActionListener(e -> parent.showPanel("menu"));
        centerPanel.add(backBtn);

        add(centerPanel, BorderLayout.CENTER);
    }

    public void loadTeamMembers() {
        try {
            int teamId = parent.getCurrentTeamId();
            TeamDao teamDao = new TeamDao();
            List<User> members = teamDao.getTeamMembersByTeamId(teamId);

            memberComboBox.removeAllItems();
            for (User u : members) {
                memberComboBox.addItem(u);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "팀원 불러오기 실패: " + e.getMessage());
        }
    }

    private void handleAssign(ActionEvent e) {
        try {
            User selected = (User) memberComboBox.getSelectedItem();
            String role = roleField.getText();
            int teamId = parent.getCurrentTeamId();

            if (selected == null || role.isBlank()) {
                JOptionPane.showMessageDialog(this, "모든 정보를 입력해주세요.");
                return;
            }

            RoleAssignmentDao dao = new RoleAssignmentDao();
            dao.assignRole(new RoleAssignment(teamId, selected.getUserId(), role));

            JOptionPane.showMessageDialog(this, "역할이 성공적으로 부여되었습니다!");
            roleField.setText("");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "역할 부여 실패: " + ex.getMessage());
        }
    }
}
