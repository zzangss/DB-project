package team_management;

import java.sql.Connection;
import java.time.LocalDate;

import team_management.dao.TeamDAO;
import team_management.domain.Team;
import team_management.dto.TeamDTO;
import team_management.dto.UserDTO;

public class TeamService {
    private final TeamDAO dao;

    public TeamService(Connection conn) {
        this.dao = new TeamDAO(conn);
    }

    public int createTeam(TeamDTO dto) throws Exception {
        Team team = new Team(0, dto.teamName, dto.subject, dto.deadline, dto.leaderId);
        int teamId = dao.createTeam(team);
        if (teamId > 0) {
            dao.addMember(teamId, dto.leaderId); // leader도 멤버로 추가
        }
        return teamId;
    }

    public boolean inviteMember(int teamId, UserDTO dto) throws Exception {
        Integer userId = dao.findUserIdByEmail(dto.email);
        if (userId == null) throw new Exception("해당 이메일 사용자가 존재하지 않음");
        return dao.addMember(teamId, userId);
    }

    public boolean delegateAndLeave(int teamId, int leaderId, int newLeaderId) throws Exception {
        if (!dao.isLeader(teamId, leaderId)) return false;
        if (!dao.isMember(teamId, newLeaderId)) return false;

        dao.updateLeader(teamId, newLeaderId);
        return dao.removeMember(teamId, leaderId);
    }

    public boolean leaveTeam(int teamId, int userId) throws Exception {
        boolean isLeader = dao.isLeader(teamId, userId);
        LocalDate deadline = dao.getDeadline(teamId);
        if (!isLeader && LocalDate.now().isBefore(deadline)) {
            System.out.println("마감일 이전 팀원 탈퇴 불가");
            return false;
        }
        return dao.removeMember(teamId, userId);
    }
}