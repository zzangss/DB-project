package DB2025Team08.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import DB2025Team08.ConnectionManager;
import DB2025Team08.dao.*;
import DB2025Team08.dto.*;
import DB2025Team08.model.User;

/**
 * 사용자 관련 비즈니스 로직 처리 클래스.
 */
public class UserService {
    /**
     * 사용자 조회 DAO 객체.
     */
    private final static UserDao userDao = new UserDao();

    /**
     * 로그인 요청을 처리한다.
     *
     * @param loginDto 로그인 요청 정보 DTO
     * @return 로그인 성공 시 사용자 객체, 실패 시 null
     * @throws SQLException DB 조회 오류 시
     */
    public static User login(LoginRequestDto loginDto) throws SQLException {
        User user = userDao.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
        if (user != null && user.getPassword().equals(loginDto.getPassword())) {
            return user;
        }
        return null;
    }

    /**
     * 사용자를 팀에 추가한다.
     *
     * @param userId 사용자 ID
     * @param teamId 팀 ID
     * @return 추가 성공 시 true, 실패 시 false
     * @throws Exception DB 처리 오류 시
     */
    public boolean addUserToTeam(int userId, int teamId) throws Exception {
        try (Connection conn = ConnectionManager.getConnection()) {
            TeamDao dao = new TeamDao();
            return dao.addMember(conn, teamId, userId);
        }
    }

    /**
     * 모든 사용자 목록을 조회한다.
     *
     * @return 사용자 목록
     * @throws Exception DB 조회 오류 시
     */
    public List<User> getAllUsers() throws Exception {
        UserDao dao = new UserDao();
        return dao.findAll();
    }

    /**
     * 사용자 회원가입을 처리한다.
     *
     * @param dto 사용자 등록 정보 DTO
     * @return 등록 성공 시 true, 이메일 중복 시 false
     * @throws SQLException DB 처리 오류 시
     */
    public static boolean register(UserDto dto) throws SQLException {
        Connection conn = null;

        try {
            // ① 트랜잭션 시작
            conn = ConnectionManager.getConnection();
            conn.setAutoCommit(false);

            if (userDao.findByEmail(dto.getEmail()) != null) {
                return false;
            }

            User user = new User();
            user.setEmail(dto.getEmail());
            user.setPassword(dto.getPassword());
            user.setName(dto.getName());
            user.setRole(dto.getRole());

            userDao.create(conn, user);

            conn.commit();

        } catch (SQLException ex) {
            // 오류 발생 시 롤백
            if (conn != null) conn.rollback();
            throw ex;
        } finally {
            // Connection 닫기
            if (conn != null) conn.close();
        }

        return true;
    }
}
