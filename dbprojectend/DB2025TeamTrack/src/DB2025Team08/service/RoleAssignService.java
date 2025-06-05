package DB2025Team08.service;

import java.sql.*;
import java.util.*;

import DB2025Team08.ConnectionManager;
import DB2025Team08.dao.*;
import DB2025Team08.model.*;

/**
 * 팀 멤버에게 역할을 할당하는 서비스 클래스.
 */
public class RoleAssignService {
    
    /**
     * 팀장 권한을 확인하고 팀원에게 역할을 할당한다.
     *
     * @param leaderId  요청한 사용자의 ID (팀장이어야 함)
     * @param teamId    역할을 할당할 팀의 ID
     * @param userId    역할을 받을 사용자의 ID
     * @param roleName  할당할 역할 이름
     * @return 역할 할당 성공 시 true, 실패 시 false
     * @throws SQLException DB 작업 중 오류 발생 시
     */
    public boolean assignRoleToMember(int leaderId, int teamId, int userId, String roleName) throws SQLException {
        Connection conn = null;

        try {
            // ① Connection 열고 트랜잭션 시작
            conn = ConnectionManager.getConnection();
            conn.setAutoCommit(false);

            TeamDao teamDao = new TeamDao();
            RoleAssignmentDao roleDao = new RoleAssignmentDao();
            
            // 팀장 여부 확인
            if (!teamDao.isLeader(leaderId, teamId)) {
                conn.close();
                return false;
            }

            // 트랜잭션 내에서 역할 설정
            roleDao.setRole(conn, teamId, userId, roleName);

            // 6. 커밋
            conn.commit();
            System.out.println("역할이 성공적으로 부여되었습니다.");
            return true;

        } catch (SQLException e) {
            // 예외 발생 시 롤백
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw e;
        } finally {
            // 7. 커넥션 닫기
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }
}
