package team_management.dao;

import java.sql.*;
import java.time.LocalDate;
import team_management.domain.Team;

public class TeamDAO {
    private final Connection conn;

    public TeamDAO(Connection conn) {
        this.conn = conn;
    }

    public int createTeam(Team team) throws SQLException {
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

    public boolean isLeader(int teamId, int userId) throws SQLException {
        String sql = "SELECT leader_id FROM DB2025_team WHERE team_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teamId);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt("leader_id") == userId;
        }
    }

    public boolean isMember(int teamId, int userId) throws SQLException {
        String sql = "SELECT 1 FROM DB2025_team_member WHERE team_id = ? AND user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teamId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
    }

    public boolean addMember(int teamId, int userId) throws SQLException {
        if (isMember(teamId, userId)) return false;

        String sql = "INSERT INTO DB2025_team_member (team_id, user_id) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teamId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean removeMember(int teamId, int userId) throws SQLException {
        String sql = "DELETE FROM DB2025_team_member WHERE team_id = ? AND user_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teamId);
            ps.setInt(2, userId);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean updateLeader(int teamId, int newLeaderId) throws SQLException {
        String sql = "UPDATE DB2025_team SET leader_id = ? WHERE team_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, newLeaderId);
            ps.setInt(2, teamId);
            return ps.executeUpdate() > 0;
        }
    }

    public LocalDate getDeadline(int teamId) throws SQLException {
        String sql = "SELECT deadline FROM DB2025_team WHERE team_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teamId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDate("deadline").toLocalDate();
            else return null;
        }
    }

    public Integer findUserIdByEmail(String email) throws SQLException {
        String sql = "SELECT user_id FROM DB2025_user WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt("user_id") : null;
        }
    }
}