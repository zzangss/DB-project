package databaseGUI;

import database.dao.TaskDao;
import database.model.Task;
import database.model.User;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.util.List;

public class TaskStatusPanel extends JPanel {

    private MainAppGUI parent;
    private JPanel cardContainer;

    public TaskStatusPanel(MainAppGUI parent) {
        this.parent = parent;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("📋 내 과제 상태 보기", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setBorder(new EmptyBorder(20, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);

        cardContainer = new JPanel();
        cardContainer.setLayout(new BoxLayout(cardContainer, BoxLayout.Y_AXIS));
        cardContainer.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(cardContainer);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        JButton backBtn = new JButton("⬅ 뒤로가기");
        backBtn.setFont(new Font("SansSerif", Font.PLAIN, 16));
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(e -> parent.showPanel("menu"));
        add(backBtn, BorderLayout.SOUTH);

        loadTasks(parent.getUserId());
    }

    public void loadTasks(int userId) {
        TaskDao taskDao = new TaskDao();
        try {
            List<Task> tasks = taskDao.getTasksByUser(new User(userId));

            if (tasks.isEmpty()) {
                JLabel noTaskLabel = new JLabel("📭 등록된 과제가 없습니다", JLabel.CENTER);
                noTaskLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
                cardContainer.add(noTaskLabel);
            } else {
                for (Task task : tasks) {
                    JPanel taskCard = createTaskCard(task);
                    cardContainer.add(taskCard);
                    cardContainer.add(Box.createVerticalStrut(10)); // 카드 간 간격
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            JLabel errorLabel = new JLabel("❗ 과제 정보를 불러오는 데 실패했습니다.");
            errorLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
            cardContainer.add(errorLabel);
        }
    }

    private JPanel createTaskCard(Task task) {
        JPanel card = new JPanel(new GridLayout(3, 1));
        card.setBackground(new Color(245, 250, 255));
        card.setBorder(new CompoundBorder(
                new LineBorder(Color.LIGHT_GRAY, 1, true),
                new EmptyBorder(10, 15, 10, 15)
        ));

        JLabel title = new JLabel("📝 과제명: " + task.getTitle());
        JLabel due = new JLabel("⏰ 마감일: " + task.getDueDate().toLocalDate());
        JLabel status = new JLabel("📌 상태: " + task.getStatus());

        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        due.setFont(new Font("SansSerif", Font.PLAIN, 14));
        status.setFont(new Font("SansSerif", Font.PLAIN, 14));

        card.add(title);
        card.add(due);
        card.add(status);

        return card;
    }
}
