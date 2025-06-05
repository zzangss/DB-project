package DB2025Team08.dao;

import java.sql.*;
import java.sql.Date;
import java.time.*;
import java.util.*;

import DB2025Team08.*;
import DB2025Team08.dto.*;
import DB2025Team08.model.*;

/**
 * 팀 관련 데이터 접근 객체 클래스.
 */
public class TeamDao {
    /**
     * 기본 생성자.
     */
    public TeamDao() {}

    /**
     * 사용자 기반 생성자.
     *
     * @param user 사용자 객체
     */
    public TeamDao(User user) {
        // 현재 사용 안 함.
    }

    /**
     * 사용자가 속한 팀 목록을 조회한다.
     *
     * @param user 사용자 객체
     * @return 팀 목록
     * @throws SQLException DB 조회 오류 시
     */
    public List<Team> getTeamsByUser(User user) throws SQLException {
        String sql = "SELECT t.team_id, t.team_name, t.subject, t.deadline, t.leader_id " +
                     "FROM DB2025_TEAM t " +
                     "JOIN DB2025_TEAM_MEMBER tm ON t.team_id = tm.team_id " +
                     "WHERE tm.user_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, user.getUserId());
            try (ResultSet rs = ps.executeQuery()) {
                List<Team> list = new ArrayList<>();
                while (rs.next()) {
                    Team team = new Team(
                        rs.getInt("team_id"),
                        rs.getString("team_name"),
                        rs.getString("subject"),
                        rs.getTimestamp("deadline").toLocalDateTime().toLocalDate(),
                        rs.getInt("leader_id")
                    );
                    list.add(team);
                }
                return list;
            }
        }
    }

    /**
     * 팀 ID로 해당 팀의 구성원 목록을 조회한다.
     *
     * @param teamId 팀 ID
     * @return 사용자 목록
     * @throws SQLException DB 조회 오류 시
     */
    public List<User> getTeamMembersByTeamId(int teamId) throws SQLException {
        List<User> members = new ArrayList<>();
        String sql = "SELECT u.user_id, u.name, u.email, ra.role_name " +
                     "FROM DB2025_USER u " +
                     "JOIN DB2025_TEAM_MEMBER tm ON u.user_id = tm.user_id " +
                     "LEFT JOIN DB2025_ROLE_ASSIGNMENT ra ON u.user_id = ra.user_id AND tm.team_id = ra.team_id " +
                     "WHERE tm.team_id = ?";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teamId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User member = new User();
                    member.setUserId(rs.getInt("user_id"));
                    member.setName(rs.getString("name"));
                    member.setEmail(rs.getString("email"));
                    member.setRole(rs.getString("role_name"));
                    members.add(member);
                }
            }
        }
        return members;
    }

    /**
     * 새로운 팀을 생성한다.
     *
     * @param conn DB 연결 객체
     * @param team 팀 객체
     * @return 생성된 팀의 ID, 실패 시 -1
     * @throws SQLException 팀 생성 중 오류 시
     */
    public int createTeam(Connection conn, Team team) throws SQLException {
        String sql = "INSERT INTO DB2025_team (team_name, subject, deadline, leader_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, team.getTeamName());
            ps.setString(2, team.getSubject());
            ps.setDate(3, Date.valueOf(team.getDeadline()));
            ps.setInt(4, team.getLeaderId());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            return rs.next() ? rs.getInt(1) : -1;
        }
    }

    /**
     * 사용자가 해당 팀의 리더인지 확인한다.
     *
     * @param teamId 팀 ID
     * @param userId 사용자 ID
     * @return 리더이면 true, 아니면 false
     * @throws SQLException DB 조회 오류 시
     */
    public boolean isLeader(int teamId, int userId) throws SQLException {
        String sql = "SELECT leader_id FROM DB2025_TEAM WHERE team_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teamId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt("leader_id") == userId;
            }
        }
    }

    /**
     * 사용자가 해당 팀의 멤버인지 확인한다.
     *
     * @param teamId 팀 ID
     * @param userId 사용자 ID
     * @return 멤버이면 true, 아니면 false
     * @throws SQLException DB 조회 오류 시
     */
    public boolean isMember(int teamId, int userId) throws SQLException {
        String sql = "SELECT 1 FROM DB2025_team_member WHERE team_id = ? AND user_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teamId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    /**
     * 팀에 멤버를 추가한다.
     *
     * @param conn DB 연결 객체
     * @param teamId 팀 ID
     * @param userId 사용자 ID
     * @return 추가 성공 시 true, 실패 시 false
     * @throws SQLException DB 삽입 오류 시
     */
    public boolean addMember(Connection conn, int teamId, int userId) throws SQLException {
        if (isMember(teamId, userId)) return false;
        String sql = "INSERT INTO DB2025_team_member (team_id, user_id) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teamId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * 팀에서 멤버를 제거한다. 역할 할당도 함께 삭제한다.
     *
     * @param conn DB 연결 객체
     * @param teamId 팀 ID
     * @param userId 사용자 ID
     * @return 제거 성공 시 true, 실패 시 false
     * @throws SQLException DB 삭제 오류 시
     */
    public boolean removeMember(Connection conn, int teamId, int userId) throws SQLException {
        String deleteRoleSql =
            "DELETE FROM DB2025_role_assignment WHERE team_id = ? AND user_id = ?";
        try (PreparedStatement psRole = conn.prepareStatement(deleteRoleSql)) {
            psRole.setInt(1, teamId);
            psRole.setInt(2, userId);
            psRole.executeUpdate();
        }

        String sql = "DELETE FROM DB2025_team_member WHERE team_id = ? AND user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teamId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * 팀 리더를 변경한다.
     *
     * @param conn DB 연결 객체
     * @param teamId 팀 ID
     * @param newLeaderId 새로운 리더 사용자 ID
     * @return 변경 성공 시 true, 실패 시 false
     * @throws SQLException DB 업데이트 오류 시
     */
    public boolean updateLeader(Connection conn, int teamId, int newLeaderId) throws SQLException {
        String sql = "UPDATE DB2025_team SET leader_id = ? WHERE team_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newLeaderId);
            ps.setInt(2, teamId);
            return ps.executeUpdate() > 0;
        }
    }

    /**
     * 팀의 마감일을 조회한다.
     *
     * @param teamId 팀 ID
     * @return 마감일(LocalDate), 없으면 null
     * @throws SQLException DB 조회 오류 시
     */
    public LocalDate getDeadline(int teamId) throws SQLException {
        String sql = "SELECT deadline FROM DB2025_team WHERE team_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teamId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDate("deadline").toLocalDate();
            else return null;
        }
    }

    /**
     * 이메일로 사용자 ID를 조회한다.
     *
     * @param email 사용자 이메일
     * @return 사용자 ID, 없으면 null
     * @throws SQLException DB 조회 오류 시
     */
    public Integer findUserIdByEmail(String email) throws SQLException {
        String sql = "SELECT user_id FROM DB2025_user WHERE email = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("user_id") : null;
        }
    }
}
