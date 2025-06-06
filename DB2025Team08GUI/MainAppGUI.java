package DB2025Team08GUI;

import javax.swing.*;

import DB2025Team08.ConnectionManager;
import DB2025Team08.dao.FeedbackDao;
import DB2025Team08.dto.FeedbackDto;
import DB2025Team08.model.Team;

import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MainAppGUI extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private int userId; // 로그인된 사용자 ID

    public MainAppGUI() {
        setTitle("DB 프로젝트 - GUI");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // ✅ 초기 패널만 추가 (동적 패널은 메서드에서 호출 시 추가)
        mainPanel.add(new LoginPanel(this), "login");
        mainPanel.add(new RegisterPanel(this), "register");
        mainPanel.add(new FeedbackPanel(this), "feedback");
        mainPanel.add(new MainMenuPanel(this), "menu");

        add(mainPanel);
        cardLayout.show(mainPanel, "login");

        setVisible(true);
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }
    
    public void showMyPagePanel() {
    	MyPagePanel myPagePanel = new MyPagePanel(this, getUserId());
    	
    	JButton backButton = new JButton("⬅ 뒤로 가기");
    	backButton.addActionListener(e -> showPanel("menu"));
    	myPagePanel.add(backButton, BorderLayout.SOUTH);
    	
    	mainPanel.add(myPagePanel, "mypage");
    	cardLayout.show(mainPanel, "mypage");
    }

    public void showTeamPanel() {
        TeamPanel teamPanel = new TeamPanel(this);
        teamPanel.loadTeamMembers(getUserId());

        JButton backButton = new JButton("⬅ 뒤로 가기");
        backButton.addActionListener(e -> showPanel("menu"));
        teamPanel.add(backButton, BorderLayout.SOUTH);

        mainPanel.add(teamPanel, "team");
        cardLayout.show(mainPanel, "team");
    }

    public void showFeedbackPanel() {
        FeedbackPanel feedbackPanel = new FeedbackPanel(this);

        JButton backButton = new JButton("⬅ 뒤로 가기");
        backButton.addActionListener(e -> showPanel("menu"));
        feedbackPanel.add(backButton, BorderLayout.SOUTH);

        mainPanel.add(feedbackPanel, "feedback");
        cardLayout.show(mainPanel, "feedback");
    }

    public void showContributionPanel() {
        ContributionPanel contributionPanel = new ContributionPanel(this);

        JButton backButton = new JButton("⬅ 뒤로 가기");
        backButton.addActionListener(e -> showPanel("menu"));
        contributionPanel.add(backButton, BorderLayout.SOUTH);

        mainPanel.add(contributionPanel, "contribution");
        cardLayout.show(mainPanel, "contribution");
    }

    public void showTaskStatusPanel() {
        TaskStatusPanel taskStatusPanel = new TaskStatusPanel(this);

        JButton backButton = new JButton("⬅ 뒤로 가기");
        backButton.addActionListener(e -> showPanel("menu"));
        taskStatusPanel.add(backButton, BorderLayout.SOUTH);

        mainPanel.add(taskStatusPanel, "task");
        cardLayout.show(mainPanel, "task");
    }
    
    public void showTeamManagePanel(Team selectedTeam) {
        // 💡 먼저 기존에 있던 teammanage 패널 제거 (덮어쓰기 방지)
        Component[] components = mainPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof TeamManagePanel) {
                mainPanel.remove(comp);
                break;
            }
        }

        // ✅ 새로 생성해서 등록
        TeamManagePanel teamManagePanel = new TeamManagePanel(this, userId, selectedTeam);
        mainPanel.add(teamManagePanel, "teammanage");
        cardLayout.show(mainPanel, "teammanage");

        // 🔍 디버그용 로그
        System.out.println("[DEBUG] 팀 관리 패널 전환 완료: " + selectedTeam.getTeamName());
    }

    public static void main(String[] args) {
        new MainAppGUI();
    }

}
