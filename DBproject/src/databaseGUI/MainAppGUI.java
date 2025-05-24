package databaseGUI;

import javax.swing.*;
import java.awt.*;

//swing 기반 gui 프로그램의 메임 프레임
//여러 화면을 전환하는 역할 창

public class MainAppGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainAppGUI() {
        setTitle("DB 프로젝트 - GUI");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new LoginPanel(this), "login");
        mainPanel.add(new RegisterPanel(this), "register");
        mainPanel.add(new TeamPanel(this), "team");
        mainPanel.add(new FeedbackPanel(this), "feedback");
        mainPanel.add(new ContributionPanel(this), "contribution");
        mainPanel.add(new MyPagePanel(this,1),"mypage");
        mainPanel.add(new MainMenuPanel(this), "menu");
        

        add(mainPanel);
        cardLayout.show(mainPanel, "login");

        setVisible(true);
    }

    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }

    public static void main(String[] args) {
        new MainAppGUI();
    }
}
