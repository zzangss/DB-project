package database.service;

import database.dao.*;
import database.dto.*;
import database.model.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TaskAssignService {
    private TaskDao taskDao;

    public TaskAssignService() {
        this.taskDao = new TaskDao();
    }

    public void assignTask(User user) {
        try {
            Scanner scanner = new Scanner(System.in);
        	TeamDao teamDao = new TeamDao(user);
        	MyPageDto dto = MyPageService.getMyPage(user);
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        	
        	//과제 입력
        	System.out.println("-- 속한 팀 --");
            List<Team> teams = dto.getTeams();
            if (teams == null || teams.isEmpty()) {
                System.out.println("  없습니다.");
            } else {
                for (Team t : teams) {
                    System.out.printf("  [%d] %s (주제: %s, 마감: %s)%n",
                                      t.getTeamId(), t.getTeamName(),
                                      t.getSubject(), t.getDeadline());
                }
            }
 
        	System.out.print("팀 id를 입력하세요: ");
            int teamId = scanner.nextInt();
            
            List<User> users = teamDao.getTeamMembersByTeamId(teamId);
            
            if (users == null || users.isEmpty()) {
                System.out.println("  없습니다.");
            } else {
                for (User u : users) {
                    System.out.printf("  [%d] %s %s \n", u.getUserId(), u.getName(), u.getRole());
                }
            }
            System.out.print("담당자 id를 입력하세요: ");
            int assignedTo = scanner.nextInt();
            scanner.nextLine();

            System.out.print("과제 제목을 입력하세요: ");
            String title = scanner.nextLine();

            System.out.print("마감일을 입력하세요 (예: 2025-06-10 23:59): ");
            String dueDateInput = scanner.nextLine();
            LocalDateTime dueDate = LocalDateTime.parse(dueDateInput, formatter);
            
            //과제 등록
            taskDao.createTask(title, dueDate, assignedTo, teamId);
            System.out.println("과제가 성공적으로 등록되었습니다.");
            
        } catch (SQLException e) {
            System.err.println("과제 등록 중 오류 발생: " + e.getMessage());
        }
    }
}
