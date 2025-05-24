package database;

import java.sql.*;
import java.time.*;
import java.util.*;

import database.ConnectionManager;
import database.dao.UserDao;
import database.model.User;

public class MainApp {
    private static Scanner scanner = new Scanner(System.in);
    private static UserDao userDao = new UserDao();
    private static User currentUser;

    public static void main(String[] args) throws SQLException {
    	
    	
        try  {
        	Connection conn = ConnectionManager.getConnection();
            while (true) {
                if (currentUser == null) {
                    showLoginMenu();
                    
                } else {
                    showMainMenu();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void showLoginMenu() throws SQLException {
        System.out.println("=== 로그인 ===");
        System.out.print("Email: ");
        String email = scanner.next();
        System.out.print("password: ");
        String password = scanner.next();
        User user = userDao.findByEmailAndPassword(email, password);
        if (user != null) {
            currentUser = user;
            System.out.println("로그인 성공: " + currentUser.getName());
        } else {
            System.out.println("로그인 실패. 다시 시도하세요.");
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
        String choice = scanner.nextLine();
        
        
    }

}
