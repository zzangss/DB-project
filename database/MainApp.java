package database;

import java.sql.*;
import java.time.*;
import java.util.*;

import database.ConnectionManager;
import database.dao.*;
import database.dto.*;
import database.model.*;
import database.service.*;

public class MainApp {
    private static Scanner scanner = new Scanner(System.in);
    private static UserDao userDao = new UserDao();
    private static TeamDao teamDao = new TeamDao();
    private static RoleAssignmentDao roleDao = new RoleAssignmentDao();
    private static TaskDao taskDao = new TaskDao();
    private static ContributionDao contributionDao = new ContributionDao();
    private static User currentUser;

    public static void main(String[] args) throws SQLException {
    	
    	
        try  {
        	Connection conn = ConnectionManager.getConnection();
        	System.out.println("로그인 : 1 회원가입 : 2");
        	int s;
        	s = scanner.nextInt();
        	if(s ==1) {
        		showLoginMenu();
        	}
        	else {
        		showRegisterMenu();
        		showLoginMenu();
        	}
            while (true) {
                showMainMenu();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showLoginMenu() throws SQLException {
        System.out.println("=== 로그인 ===");
        System.out.print("Email: ");
        String email = scanner.next();
        System.out.print("Password: ");
        String password = scanner.next();

        LoginRequestDto loginDto = new LoginRequestDto(email, password);
        User user = UserService.login(loginDto);

        if (user != null) {
            currentUser = user;
            System.out.println("로그인 성공! ID: " + currentUser.getUserId() + " " + currentUser.getRole());
        } else {
            System.out.println("로그인 실패. 다시 시도하세요.");
        }
    }

    private static void showRegisterMenu() throws SQLException {
        System.out.println("=== 회원가입 ===");
        System.out.print("이메일: ");
        String email = scanner.next();
        System.out.print("비밀번호: ");
        String password = scanner.next();
        System.out.print("이름: ");
        String name = scanner.next();
        System.out.print("역할 (user/admin): ");
        String role = scanner.next();

        UserDto dto = new UserDto(email, password, name, role);
        boolean success = UserService.register(dto);
        if (success) {
            System.out.println("회원가입이 완료되었습니다.");
        } else {
            System.out.println("이미 사용 중인 이메일입니다.");
        }
    }
    
    private static void showMainMenu() throws SQLException {
        System.out.println("\n=== 메인 메뉴 ===");
        System.out.println("1. 팀 목록 조회");
        System.out.println("2. 마이페이지");
        System.out.println("3. 팀 생성");
        System.out.println("4. 팀원 초대");
        System.out.println("5. 초대 수락");
        System.out.println("6. 팀 탈퇴");
        System.out.println("7. 역할 배정");
        System.out.println("8. 과제 등록");
        System.out.println("9. 내 과제 조회");
        System.out.println("10. 과제 상태 수정");
        System.out.println("0. 로그아웃");
        System.out.print("메뉴 선택: ");
        int choice = scanner.nextInt();
        
        switch(choice) {
        case 1:
        	break;
        case 2:
        	MyPageService.showMyPage(currentUser);
        	break;
        case 7:
        	RoleAssignService.assignRoleToMember(currentUser);
        case 8:
        	
        
        default:
        	break;	
        }
    }   
    
    	
    
   }

