package DB2025Team08GUI;

import DB2025Team08.dao.MeetingDao;
import DB2025Team08.dto.MeetingDto;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MeetingPanel extends JPanel {

    private MainAppGUI parent;
    private DefaultListModel<String> meetingModel;
    private JList<String> meetingList;

    public MeetingPanel(MainAppGUI parent) {
        this.parent = parent;
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("📅 회의록 목록", JLabel.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        meetingModel = new DefaultListModel<>();
        meetingList = new JList<>(meetingModel);
        JScrollPane scrollPane = new JScrollPane(meetingList);
        add(scrollPane, BorderLayout.CENTER);

        // ✅ 버튼 패널 추가
        JPanel buttonPanel = new JPanel();
        JButton backBtn = new JButton("⬅ 뒤로가기");
        JButton writeBtn = new JButton("➕ 회의록 작성");

        backBtn.addActionListener(e -> parent.showPanel("menu"));
        writeBtn.addActionListener(e -> parent.showPanel("meetingRegister")); // meetingRegister 패널로 이동

        buttonPanel.add(backBtn);
        buttonPanel.add(writeBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        loadMeetings(); // ✅ 생성과 동시에 로드
    }

    private void loadMeetings() {
        try {
            meetingModel.clear(); // 기존 내용 초기화
            int teamId = parent.getCurrentTeamId();

            MeetingDao dao = new MeetingDao();
            List<MeetingDto> meetings = dao.findMeetingsByTeamId(teamId); // ✅ 이 메서드가 dao에 구현돼 있어야 함

            if (meetings.isEmpty()) {
                meetingModel.addElement("회의록이 없습니다.");
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                for (MeetingDto meeting : meetings) {
                    String info = "[" + meeting.getMeetingDate().format(formatter) + "] " +
                                  meeting.getContent() + " / 결정: " + meeting.getDecision();
                    meetingModel.addElement(info);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            meetingModel.addElement("불러오는 중 오류 발생: " + e.getMessage());
        }
    }

    // ✅ 외부에서 새로고침할 수 있도록 메서드 제공
    public void refreshMeetings() {
        loadMeetings();
    }
}
