package DB2025Team08GUI;

import DB2025Team08.model.User;
import DB2025Team08.service.TeamService;
import DB2025Team08.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TeamMemberAddPanel extends JPanel {

    private MainAppGUI parent;
    private JComboBox<User> userComboBox;
    private DefaultListModel<String> memberListModel;
    private JList<String> memberList;
    private int teamId;

    public TeamMemberAddPanel(MainAppGUI parent, int teamId) {
        this.parent = parent;
        this.teamId = teamId;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("👤 팀원 수동 추가", JLabel.CENTER);
        title.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());

        // 사용자 콤보박스
        userComboBox = new JComboBox<>();
        loadUserList();

        JButton addBtn = new JButton("➕ 팀에 추가");
        addBtn.addActionListener(e -> addUserToTeam());

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("사용자 선택: "));
        topPanel.add(userComboBox);
        topPanel.add(addBtn);

        centerPanel.add(topPanel, BorderLayout.NORTH);

        // 팀원 목록
        memberListModel = new DefaultListModel<>();
        memberList = new JList<>(memberListModel);
        JScrollPane scrollPane = new JScrollPane(memberList);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        JButton backBtn = new JButton("⬅ 돌아가기");
        backBtn.addActionListener(e -> parent.showPanel("menu"));
        add(backBtn, BorderLayout.SOUTH);

        refreshTeamMembers();
    }

    private void loadUserList() {
        try {
            UserService userService = new UserService();
            List<User> users = userService.getAllUsers();
            userComboBox.removeAllItems();
            for (User user : users) {
                userComboBox.addItem(user);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "사용자 목록 로드 실패: " + e.getMessage());
        }
    }

    private void addUserToTeam() {
        User selectedUser = (User) userComboBox.getSelectedItem();
        if (selectedUser == null) {
            JOptionPane.showMessageDialog(this, "추가할 사용자를 선택해주세요.");
            return;
        }
        try {
            UserService userService = new UserService();
            boolean success = userService.addUserToTeam(selectedUser.getUserId(), teamId);
            if (success) {
                JOptionPane.showMessageDialog(this, "사용자가 팀에 추가되었습니다.");
                refreshTeamMembers();
            } else {
                JOptionPane.showMessageDialog(this, "이미 팀에 속한 사용자일 수 있습니다.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "추가 실패: " + e.getMessage());
        }
    }

    private void refreshTeamMembers() {
        try {
            TeamService teamService = new TeamService();
            List<User> members = teamService.getMembersOfTeam(teamId);
            memberListModel.clear();
            for (User user : members) {
                memberListModel.addElement(user.getName() + " (" + user.getEmail() + ")");
            }
        } catch (Exception e) {
            memberListModel.clear();
            memberListModel.addElement("팀원 목록 불러오기 실패: " + e.getMessage());
        }
    }
}
