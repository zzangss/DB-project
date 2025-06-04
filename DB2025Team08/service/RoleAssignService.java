package DB2025Team08.service;

import java.sql.*;
import java.util.*;

import DB2025Team08.ConnectionManager;
import DB2025Team08.dao.*;
import DB2025Team08.model.*;

public class RoleAssignService {
	
	// 팀장의 유저id, 팀 id, 역할을 받을 유저 id, 역할 이름
    public boolean assignRoleToMember(int leaderId, int teamId, int userId, String roleName) throws SQLException {
        Connection conn = null;

        try {
            // ① Connection 열고 트랜잭션 시작
            conn = ConnectionManager.getConnection();
            conn.setAutoCommit(false);

            TeamDao teamDao = new TeamDao();
            RoleAssignmentDao roleDao = new RoleAssignmentDao();
            
            if(!teamDao.isLeader(leaderId, teamId)) {
            	conn.close();
            	return false; //리더가 아닌 경우 역할 부여 불가
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
