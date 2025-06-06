package DB2025Team08.dao;

import java.sql.*;
import java.util.*;

import DB2025Team08.ConnectionManager;
import DB2025Team08.model.User;

/**
 * 사용자 관련 데이터 접근 객체 클래스.
 */
public class UserDao {
    /**
     * 새 사용자를 삽입한다.
     *
     * @param conn 데이터베이스 연결 객체
     * @param user 삽입할 사용자 객체
     * @throws SQLException SQL 실행 오류 발생 시
     */
    public void create(Connection conn, User user) throws SQLException {
        String sql = ""
            + "INSERT INTO DB2025_USER "
            + "(email, password, name, role) "
            + "VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(
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

    /**
     * 사용자 ID로 사용자 정보를 조회한다.
     *
     * @param userId 조회할 사용자 ID
     * @return 조회된 사용자 객체, 없으면 null
     * @throws SQLException SQL 실행 오류 발생 시
     */
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

    /**
     * 이메일과 비밀번호로 사용자 정보를 조회한다.
     *
     * @param userEmail 조회할 이메일
     * @param userPassword 조회할 비밀번호
     * @return 조회된 사용자 객체, 없으면 null
     * @throws SQLException SQL 실행 오류 발생 시
     */
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

    /**
     * 이메일로 사용자 정보를 조회한다.
     *
     * @param email 조회할 이메일
     * @return 조회된 사용자 객체, 없으면 null
     * @throws SQLException SQL 실행 오류 발생 시
     */
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

    /**
     * 팀 ID로 해당 팀의 사용자 목록을 조회한다.
     *
     * @param teamId 조회할 팀 ID
     * @return 사용자 ID를 키로 하는 사용자 맵
     * @throws SQLException SQL 실행 오류 발생 시
     */
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

    /**
     * 모든 사용자를 조회한다.
     *
     * @return 사용자 목록
     * @throws SQLException SQL 실행 오류 발생 시
     */
    public List<User> findAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT user_id, email, password, name, role, created_at FROM DB2025_USER";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setName(rs.getString("name"));
                u.setRole(rs.getString("role"));
                u.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                users.add(u);
            }
        }

        return users;
    }
}
