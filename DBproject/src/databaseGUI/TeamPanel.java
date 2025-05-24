package databaseGUI;
//로그인한 사용자의 팀 구성원들을 보여주는 화면
//추후 db에서 진짜 티무언 정보를 불러오도록 확장 가능 
//화면 틀과 구조 먼저 만들었습니다. 

import javax.swing.*;
import java.awt.*;

public class TeamPanel extends JPanel {
    public TeamPanel(MainAppGUI app) {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("👥 팀 구성원 목록", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // 팀 구성원 목록을 보여줄 리스트 박스
        DefaultListModel<String> teamModel = new DefaultListModel<>();
        JList<String> teamList = new JList<>(teamModel);
        JScrollPane scrollPane = new JScrollPane(teamList);
        add(scrollPane, BorderLayout.CENTER);

        // 뒤로가기 버튼
        JButton backBtn = new JButton("뒤로가기");
        add(backBtn, BorderLayout.SOUTH);

        // 테스트용 가짜 팀원 데이터
        teamModel.addElement("진서영 (팀장)");
        teamModel.addElement("송유진");
        teamModel.addElement("염승희");
        teamModel.addElement("황채원");
        teamModel.addElement("정가인");

        backBtn.addActionListener(e -> {
            app.showPanel("login");  // 나중에 "메인 메뉴"로 바꿔도 됨
        });
    }
}