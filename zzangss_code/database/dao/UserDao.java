package database.dao;

import database.ConnectionManager;
import database.model.User;

import java.sql.*;
import java.util.*;

public class UserDao {
    /** 새 사용자 INSERT */
    public void create(User user) throws SQLException {
        String sql = ""
            + "INSERT INTO DB2025_USER "
            + "(email, password, name, role) "
            + "VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                 sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.setString(4, user.getRole());
            ps.executeUpdate();

            // 생성된 PK(user_id) 가져오기
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setUserId(rs.getInt(1));
                }
            }
        }
    }

    /** PK로 사용자 조회 */
    public static User findById(int userId) throws SQLException {
        String sql = ""
            + "SELECT user_id, email, password, name, role, created_at "
            + "FROM DB2025_USER "
            + "WHERE user_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                User u = new User();
                u.setUserId(   rs.getInt   ("user_id")                );
                u.setEmail(    rs.getString("email")                  );
                u.setPassword( rs.getString("password")               );
                u.setName(     rs.getString("name")                   );
                u.setRole(     rs.getString("role")                   );
                u.setCreatedAt(
                    rs.getTimestamp("created_at")
                      .toLocalDateTime()
                );
                return u;
            }
        }
    }
    
    /** 유저의 이메일과 비밀번호로 유저 정보를 반환하는 메서드 */
    public User findByEmailAndPassword(String userEmail, String userPassword) throws SQLException {
        String sql = ""
            + "SELECT user_id, email, password, name, role, created_at "
            + "FROM DB2025_USER "
            + "WHERE email = ? AND password = ? ";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userEmail);
            ps.setString(2, userPassword);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                User u = new User();
                u.setUserId(   rs.getInt   ("user_id")                );
                u.setEmail(    rs.getString("email")                  );
                u.setPassword( rs.getString("password")               );
                u.setName(     rs.getString("name")                   );
                u.setRole(     rs.getString("role")                   );
                u.setCreatedAt(
                    rs.getTimestamp("created_at")
                      .toLocalDateTime()
                );
                return u;
            }
        }
    }
    
    public User findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM DB2025_USER WHERE email = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setUserId(rs.getInt("user_id"));
                    u.setEmail(rs.getString("email"));
                    u.setPassword(rs.getString("password"));
                    u.setName(rs.getString("name"));
                    u.setRole(rs.getString("role"));
                    return u;
                }
                return null;
            }
        }
    }
    
    public Map<Integer, User> findUsersByTeamId(int teamId) throws SQLException {
        Map<Integer, User> map = new HashMap<>();
        String sql = "SELECT u.user_id, u.name "+
        			"FROM DB2025_USER u "+
        			"JOIN DB2025_TEAM_MEMBER m ON u.user_id = m.user_id "+
        			"WHERE m.team_id = ?";
        
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teamId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User u = new User();
                    u.setUserId(rs.getInt("user_id"));
                    u.setName(rs.getString("name"));
                    map.put(u.getUserId(), u);
                }
            }
        }

        return map;
    }


}