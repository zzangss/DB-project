package database.service;

import database.model.*;
import database.dao.*;
import database.dto.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Service 레이어: 마이페이지 데이터 조합
 */
public class MyPageService {
    private static TeamDao teamDao = new TeamDao();
    private static RoleAssignmentDao roleDao = new RoleAssignmentDao();
    private static TaskDao taskDao = new TaskDao();
    private static ContributionDao contributionDao = new ContributionDao();
    private static Scanner scanner = new Scanner(System.in);
    
    /**
     * 특정 사용자의 팀, 역할, 과제, 기여도 정보를 DTO에 담아 반환
     * @param user 조회할 사용자
     * @return MyPageDto
     * @throws SQLException DB 오류 발생 시
     */
    public static MyPageDto getMyPage(User user) throws SQLException {
        MyPageDto dto = new MyPageDto();

        dto.setTeams(teamDao.getTeamsByUser(user));
        dto.setRoles(roleDao.getRolesByUser(user));
        dto.setTasks(taskDao.getTasksByUser(user));
        dto.setContributions(contributionDao.getContributionsByUser(user));

        return dto;
    }
    
    public static void showMyPage(User User) throws SQLException {
        System.out.println("\n=== 마이페이지 ===");
        MyPageDto dto = MyPageService.getMyPage(User);

        System.out.println("1. 내가 속한 팀 보기");
        System.out.println("2. 나의 역할 보기");
        System.out.println("3. 기여도 보기"); // 👈 추가
        System.out.println("0. 뒤로가기");
        int sel = scanner.nextInt();
        scanner.nextLine();

        switch(sel) {
        case 1:
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
            break;
        case 2:
        	System.out.println("\n-- 할당된 역할 --");
            List<RoleAssignment> roles = dto.getRoles();
            if (roles == null || roles.isEmpty()) {
                System.out.println("  없습니다.");
            } else {
                for (RoleAssignment r : roles) {
                    System.out.printf("  팀 %d: %s%n",
                                      r.getTeamId(), r.getRoleName());
                }
            }
            break;
        case 3:
        	System.out.println("\n-- 기여도 --");
            List<Contribution> contributions = dto.getContributions();
            if (contributions == null || contributions.isEmpty()) {
                System.out.println("  없습니다.");
            } else {
                for (Contribution c : contributions) {
                    System.out.printf("  팀 %d: 제출 %d회, 참여 %d회, 보너스 %d, 기여도 %.1f%%%n",
                                      c.getTeamId(), c.getSubmittedCount(),
                                      c.getAttendedCount(), c.getBonus(),
                                      c.getPercentage());
                }
                System.out.println("기여도를 조회할 팀 ID를 입력하세요: ");
                int teamId = scanner.nextInt();
                ContributionService.analyzeAndDisplayContribution(teamId);
            }
            break;
           	default:
            	break;
        }
    }
}