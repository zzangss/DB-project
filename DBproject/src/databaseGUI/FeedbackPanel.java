package databaseGUI;
//db 의 feedback 테이블에서 내가 받은 피드백을 가져와서 보여주기 
//나중에 로그인한 유저의 user_id 를 기준으로 필터링 가능

import javax.swing.*;
import java.awt.*;

public class FeedbackPanel extends JPanel {
    public FeedbackPanel(MainAppGUI app) {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("📩 받은 피드백", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        DefaultListModel<String> feedbackModel = new DefaultListModel<>();
        JList<String> feedbackList = new JList<>(feedbackModel);
        JScrollPane scrollPane = new JScrollPane(feedbackList);
        add(scrollPane, BorderLayout.CENTER);

        JButton backBtn = new JButton("뒤로가기");
        add(backBtn, BorderLayout.SOUTH);

        // 🧪 테스트용 피드백 목록
        feedbackModel.addElement("팀장님 감사합니다! (from 쇼타로)");
        feedbackModel.addElement("제출 시간 꼭 지켜주세요! (from 소희)");
        feedbackModel.addElement("고생 많으셨어요! (from 은석)");

        backBtn.addActionListener(e -> {
            app.showPanel("team");  // 필요시 로그인 화면 등으로 변경 가능
        });
    }
}
