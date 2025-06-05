package DB2025Team08.dao;

import java.sql.*;
import java.sql.Date;
import java.time.*;
import java.util.*;

import DB2025Team08.*;
import DB2025Team08.dto.*;
import DB2025Team08.model.*;

public class TeamDao {
    public TeamDao() {}

    public TeamDao(User user) {
        // 현재는 사용하지 않지만 필요 시 활용 가능
    }

    // ✅ [1] 사용자가 속한 팀 목록 조회
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

    // ✅ [2] 팀 ID로 팀 구성원 목록 조회
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
    
    // 새로운 팀 만들기
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

    // ✅ [3] 이 사용자가 해당 팀의 리더인지 확인
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
    public boolean addMember(Connection conn, int teamId, int userId) throws SQLException {
        if (isMember(teamId, userId)) return false;

        String sql = "INSERT INTO DB2025_team_member (team_id, user_id) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teamId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean removeMember(Connection conn, int teamId, int userId) throws SQLException {
        String sql = "DELETE FROM DB2025_team_member WHERE team_id = ? AND user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teamId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateLeader(Connection conn, int teamId, int newLeaderId) throws SQLException {
        String sql = "UPDATE DB2025_team SET leader_id = ? WHERE team_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newLeaderId);
            ps.setInt(2, teamId);
            return ps.executeUpdate() > 0;
        }
    }

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
