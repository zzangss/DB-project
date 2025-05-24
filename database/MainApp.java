package database.dao;

import database.ConnectionManager;
import database.model.User;

import java.sql.*;

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
    public User findById(int userId) throws SQLException {
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
}
