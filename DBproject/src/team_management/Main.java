package team_management;

import team_management.dto.TeamDTO;
import team_management.dto.UserDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;

public class Main {
	
	static final String JDBC_URL="jdbc:mysql://localhost:3306/DB2025Team08";
	static final String USER="DB2025Team08";
	static final String PASS="DB2025Team08";
	
	
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASS)) {
            TeamService service = new TeamService(conn);

            // 팀 생성
            TeamDTO newTeam = new TeamDTO();
            newTeam.teamName = "AI 프로젝트";
            newTeam.subject = "인공지능";
            newTeam.deadline = LocalDate.of(2025, 6, 15);
            newTeam.leaderId = 1;
            int teamId = service.createTeam(newTeam);

            // 팀원 초대
            UserDTO user = new UserDTO();
            user.email = "eunseok@example.com";
            service.inviteMember(teamId, user);

            // 팀장 위임 후 탈퇴
            service.delegateAndLeave(teamId, 1, 2);

            // 팀원 탈퇴
            service.leaveTeam(teamId, 2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}