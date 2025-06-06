package DB2025Team08GUI;

import javax.swing.*;

import DB2025Team08.ConnectionManager;
import DB2025Team08.dao.TeamDao;
import DB2025Team08.model.Team;
import DB2025Team08.model.User;
import DB2025Team08.service.TeamService;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TeamManagePanel extends JPanel {

    private MainAppGUI parent;
    private int userId;
    private Team team;
    private TeamService teamService;
    private TeamDao teamDao;

    private DefaultListModel<String> memberListModel;
    private JList<String> memberList;
    private JComboBox<String> memberComboBox;

    private List<User> teamMembers;

    public TeamManagePanel(MainAppGUI parent, int userId, Team team) {
        this.parent = parent;
        this.userId = userId;
        this.team = team;

        try {
            Connection conn = ConnectionManager.getConnection();
            this.teamDao = new TeamDao(); // 기본 생성자 사용
            this.teamService = new TeamService(); // TeamService는 conn 필요
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "DB 연결 실패: " + e.getMessage());
            return;
        }

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("👑 팀 관리: " + team.getTeamName(), JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        memberListModel = new DefaultListModel<>();
        memberList = new JList<>(memberListModel);
        add(new JScrollPane(memberList), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(3, 1));

        JPanel delegatePanel = new JPanel();
        memberComboBox = new JComboBox<>();
        JButton delegateBtn = new JButton("팀장 위임 후 탈퇴");
        delegateBtn.addActionListener(e -> delegateAndLeave());
        delegatePanel.add(new JLabel("위임 대상: "));
        delegatePanel.add(memberComboBox);
        delegatePanel.add(delegateBtn);

        JButton leaveBtn = new JButton("팀 탈퇴");
        leaveBtn.addActionListener(e -> leaveTeam());

        JButton backBtn = new JButton("뒤로가기");
        backBtn.addActionListener(e -> parent.showPanel("menu"));

        bottomPanel.add(delegatePanel);
        bottomPanel.add(leaveBtn);
        bottomPanel.add(backBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        loadTeamMembers();
    }

    private void loadTeamMembers() {
        try {
            teamMembers = teamDao.getTeamMembersByTeamId(team.getTeamId());
            memberListModel.clear();
            memberComboBox.removeAllItems();

            for (User member : teamMembers) {
                String display = member.getName() + " (" + member.getRole() + ")";
                memberListModel.addElement(display);

                if (member.getUserId() != userId) {
                    memberComboBox.addItem(member.getName());
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "팀원 정보를 불러오는 데 실패했습니다.\n" + e.getMessage());
        }
    }

    private void delegateAndLeave() {
        String selectedName = (String) memberComboBox.getSelectedItem();
        if (selectedName == null) {
            JOptionPane.showMessageDialog(this, "위임할 팀원을 선택하세요.");
            return;
        }

        int newLeaderId = -1;
        for (User member : teamMembers) {
            if (member.getName().equals(selectedName)) {
                newLeaderId = member.getUserId();
                break;
            }
        }

        try {
            boolean success = teamService.delegateAndLeave(team.getTeamId(), userId, newLeaderId);
            if (success) {
                JOptionPane.showMessageDialog(this, "팀장을 위임하고 탈퇴했습니다.");
                parent.showPanel("menu");
            } else {
                JOptionPane.showMessageDialog(this, "위임 또는 탈퇴에 실패했습니다.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "오류: " + e.getMessage());
        }
    }

    private void leaveTeam() {
        try {
            boolean success = teamService.leaveTeam(team.getTeamId(), userId);
            if (success) {
                JOptionPane.showMessageDialog(this, "팀에서 탈퇴했습니다.");
                parent.showPanel("menu");
            } else {
                JOptionPane.showMessageDialog(this, "탈퇴 실패 (마감일 전 탈퇴 불가 or 위임 이전 탈퇴 시도)");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "오류: " + e.getMessage());
        }
    }
}
