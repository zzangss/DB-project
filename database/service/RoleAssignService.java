package database.service;

import java.sql.*;
import java.time.*;
import java.util.*;

import database.ConnectionManager;
import database.dao.*;
import database.dto.*;
import database.model.*;
import database.service.*;

public class RoleAssignService {
	public static void assignRoleToMember(User currentUser) throws SQLException {
	    Scanner scanner = new Scanner(System.in);
	    TeamDao teamDao = new TeamDao();
	    RoleAssignmentDao roleDao = new RoleAssignmentDao();

	    // 1. 팀장인 팀 ID 가져오기
	    List<Team> teams = teamDao.getTeamsByUser(currentUser);
	    if (teams.isEmpty()) {
	        System.out.println("팀에 속해있지 않습니다.");
	        return;
	    }

	    System.out.println("당신이 속한 팀 목록:");
	    for (int i = 0; i < teams.size(); i++) {
	        System.out.println((i + 1) + ". " + teams.get(i).getTeamName() + " (ID: " + teams.get(i).getTeamId() + ")");
	    }

	    System.out.print("역할을 부여할 팀을 선택하세요 (번호 입력): ");
	    int selectedIndex = scanner.nextInt() - 1;
	    Team selectedTeam = teams.get(selectedIndex);

	    // 3. 사용자가 해당 팀의 팀장인지 확인
	    if (selectedTeam.getLeaderId() != currentUser.getUserId()) {
	        System.out.println("이 팀의 팀장이 아닙니다. 역할을 부여할 수 없습니다.");
	        return;
	    }

	    // 4. 팀원 목록 조회
	    List<User> members = teamDao.getTeamMembersByTeamId(selectedTeam.getTeamId());
	    // 팀장 본인은 제외
	    members.removeIf(m -> m.getUserId() == currentUser.getUserId());

	    if (members.isEmpty()) {
	        System.out.println("역할을 부여할 팀원이 없습니다.");
	        return;
	    }

	    System.out.println("팀원 목록:");
	    for (int i = 0; i < members.size(); i++) {
	    	User u = new User();
	    	u = UserDao.findById(members.get(i).getUserId());
	    	
	        System.out.println((i + 1) + ". " + members.get(i).getName() + " (ID: " +members.get(i).getUserId() + ")");
	    }

	    System.out.print("역할을 부여할 팀원을 선택하세요 (번호 입력): ");
	    int memberIndex = scanner.nextInt() - 1;
	    User selectedMember = members.get(memberIndex);

	    // 5. 역할 입력 및 부여
	    System.out.print("부여할 역할을 입력하세요: ");
	    scanner.nextLine(); // flush newline
	    String roleName = scanner.nextLine();

	    roleDao.setRole(selectedTeam.getTeamId(), selectedMember.getUserId(), roleName);
	    System.out.println("역할이 성공적으로 부여되었습니다.");
	}

}
