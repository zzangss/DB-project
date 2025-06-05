package DB2025Team08GUI;

import javax.swing.*;

import DB2025Team08.dao.TeamDao;
import DB2025Team08.model.Team;
import DB2025Team08.model.User;

import java.awt.*;
import java.util.List;

public class MainMenuPanel extends JPanel {

    private MainAppGUI parent;

    public MainMenuPanel(MainAppGUI parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("🐯 TeamTrack Menu", JLabel.CENTER);
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 100, 10, 100));

        buttonPanel.add(createSmallButton("👥 팀 구성원 보기", e -> parent.showTeamPanel()));
        buttonPanel.add(Box.createVerticalStrut(8));

        buttonPanel.add(createSmallButton("🙋 마이페이지", e -> parent.showMyPagePanel()));
        buttonPanel.add(Box.createVerticalStrut(8));

        // 팀 관리 버튼
        buttonPanel.add(createSmallButton("🛠 팀 관리", e -> openTeamManagePanel()));
        buttonPanel.add(Box.createVerticalStrut(8));

        // 팀원 추가 버튼
        buttonPanel.add(createSmallButton("➕ 팀원 추가", e -> openTeamMemberAddPanel()));
        buttonPanel.add(Box.createVerticalStrut(8));

        buttonPanel.add(createSmallButton("📝 피드백 보기", e -> parent.showFeedbackPanel()));
        buttonPanel.add(Box.createVerticalStrut(8));

        buttonPanel.add(createSmallButton("📊 기여도 보기", e -> parent.showContributionPanel()));
        buttonPanel.add(Box.createVerticalStrut(8));

        buttonPanel.add(createSmallButton("📋 과제 상태 보기", e -> parent.showTaskStatusPanel()));
        buttonPanel.add(Box.createVerticalStrut(8));

        buttonPanel.add(createSmallButton("📋 회의록", e -> {parent.setCurrentTeamId(1); parent.showMeetingPanel(); }));
        buttonPanel.add(Box.createVerticalStrut(8));

        // 피드백 보내기 버튼 - 현재 팀 설정 후 화면 전환
        buttonPanel.add(createSmallButton("✉ 피드백 보내기", e -> openFeedbackSendPanel()));
        buttonPanel.add(Box.createVerticalStrut(8));
        
        buttonPanel.add(createSmallButton("📌 과제 등록", e -> openTaskRegisterPanel()));
        buttonPanel.add(Box.createVerticalStrut(8));
        
        buttonPanel.add(createSmallButton("🎭 역할 부여", e -> openRoleAssignPanel()));
        buttonPanel.add(Box.createVerticalStrut(8));
        
        buttonPanel.add(createSmallButton("🆕 팀 만들기",e -> parent.showTeamCreatePanel()));
        buttonPanel.add(Box.createVerticalStrut(8));


        
        buttonPanel.add(createSmallButton("🔐 로그아웃", e -> parent.showPanel("login")));

        add(buttonPanel, BorderLayout.CENTER);
    }

    private JButton createSmallButton(String text, java.awt.event.ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Dialog", Font.PLAIN, 14));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(listener);
        return button;
    }

    private void openTeamManagePanel() {
        try {
            int userId = parent.getUserId();
            TeamDao dao = new TeamDao();
            List<Team> teamList = dao.getTeamsByUser(new User(userId));

            if (teamList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "소속된 팀이 없습니다.");
                return;
            }

            Team selectedTeam = (Team) JOptionPane.showInputDialog(
                this,
                "관리할 팀을 선택하세요",
                "팀 선택",
                JOptionPane.PLAIN_MESSAGE,
                null,
                teamList.toArray(),
                teamList.get(0)
            );

            if (selectedTeam != null) {
                parent.showTeamManagePanel(selectedTeam);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "팀 정보를 불러오는 중 오류 발생:\n" + e.getMessage());
        }
    }

    // 팀원 수동 추가 패널 열기
    private void openTeamMemberAddPanel() {
        try {
            int userId = parent.getUserId();
            TeamDao dao = new TeamDao();
            List<Team> teams = dao.getTeamsByUser(new User(userId));

            if (teams.isEmpty()) {
                JOptionPane.showMessageDialog(this, "소속된 팀이 없습니다.");
                return;
            }

            Team selectedTeam = (Team) JOptionPane.showInputDialog(
                this,
                "팀원 추가할 팀을 선택하세요",
                "팀 선택",
                JOptionPane.PLAIN_MESSAGE,
                null,
                teams.toArray(),
                teams.get(0)
            );

            if (selectedTeam != null) {
                parent.showTeamMemberAddPanel(selectedTeam.getTeamId());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "팀 정보 불러오기 오류:\n" + e.getMessage());
        }
    }

    // 피드백 보내기 패널 열기 - 현재 팀 ID 세팅
    private void openFeedbackSendPanel() {
        try {
            int userId = parent.getUserId();
            TeamDao dao = new TeamDao();
            List<Team> teams = dao.getTeamsByUser(new User(userId));

            if (teams.isEmpty()) {
                JOptionPane.showMessageDialog(this, "소속된 팀이 없습니다.");
                return;
            }

            Team selectedTeam = (Team) JOptionPane.showInputDialog(
                this,
                "피드백을 보내는 팀을 선택하세요",
                "팀 선택",
                JOptionPane.PLAIN_MESSAGE,
                null,
                teams.toArray(),
                teams.get(0)
            );

            if (selectedTeam != null) {
                parent.setCurrentTeamId(selectedTeam.getTeamId());
                parent.showFeedbackSendPanel(); 
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "팀 정보 불러오기 오류:\n" + e.getMessage());
        }
    }
    
 // 아래 메서드 추가
    private void openTaskRegisterPanel() {
        try {
            int userId = parent.getUserId();
            TeamDao dao = new TeamDao();
            List<Team> teams = dao.getTeamsByUser(new User(userId));

            if (teams.isEmpty()) {
                JOptionPane.showMessageDialog(this, "소속된 팀이 없습니다.");
                return;
            }

            Team selectedTeam = (Team) JOptionPane.showInputDialog(
                this,
                "과제를 등록할 팀을 선택하세요",
                "팀 선택",
                JOptionPane.PLAIN_MESSAGE,
                null,
                teams.toArray(),
                teams.get(0)
            );

            if (selectedTeam != null) {
                parent.setCurrentTeamId(selectedTeam.getTeamId());

                // taskRegisterPanel 불러와서 멤버 로드
                TaskRegisterPanel panel = new TaskRegisterPanel(parent);
                panel.loadTeamMembers();
                parent.getMainPanel().add(panel, "taskRegister");
                parent.showPanel("taskRegister");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "팀 정보 불러오기 오류:\n" + e.getMessage());
        }
    }
    
    private void openRoleAssignPanel() {
        try {
            int userId = parent.getUserId();
            TeamDao dao = new TeamDao();
            List<Team> teams = dao.getTeamsByUser(new User(userId));

            if (teams.isEmpty()) {
                JOptionPane.showMessageDialog(this, "소속된 팀이 없습니다.");
                return;
            }

            Team selectedTeam = (Team) JOptionPane.showInputDialog(
                this,
                "역할을 부여할 팀을 선택하세요",
                "팀 선택",
                JOptionPane.PLAIN_MESSAGE,
                null,
                teams.toArray(),
                teams.get(0)
            );

            if (selectedTeam != null) {
                boolean isLeader = dao.isLeader(selectedTeam.getTeamId(), userId);
                if (!isLeader) {
                    JOptionPane.showMessageDialog(this, "⚠️ 역할 부여는 팀장만 수행할 수 있습니다.");
                    return;
                }

                // 팀 ID 저장
                parent.setCurrentTeamId(selectedTeam.getTeamId());
                parent.showRoleAssignPanel();
                // 역할 부여 패널 생성 및 등록
                RoleAssignPanel roleAssignPanel = new RoleAssignPanel(parent);
                parent.getMainPanel().add(roleAssignPanel, "roleAssign");

          
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "팀 정보 불러오기 오류:\n" + e.getMessage());
        }
    }



}
