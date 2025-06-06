package DB2025Team08.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import DB2025Team08.ConnectionManager;
import DB2025Team08.dao.TeamDao;
import DB2025Team08.dto.TeamDto;
import DB2025Team08.dto.UserDto;
import DB2025Team08.model.Team;
import DB2025Team08.model.User;

public class TeamService {
    private final TeamDao dao;

    public TeamService() {
        this.dao = new TeamDao();
    }

    /**
     * 팀 생성과 리더 멤버 추가를 한 트랜잭션으로 묶음
     */
    public int createTeam(TeamDto dto) throws Exception {
        Connection conn = null;
        try {
            conn = ConnectionManager.getConnection();
            conn.setAutoCommit(false);

            Team team = new Team(
                0,
                dto.getTeamName(),
                dto.getSubject(),
                dto.getDeadline(),
                dto.getLeaderId()
            );
            // ① 팀 생성 → PK(teamId) 반환
            int teamId = dao.createTeam(conn, team);
            if (teamId <= 0) {
                conn.rollback();
                throw new Exception("팀 생성에 실패했습니다.");
            }

            // ② 생성된 팀에 리더도 멤버로 추가
            boolean added = dao.addMember(conn, teamId, dto.getLeaderId());
            if (!added) {
                conn.rollback();
                throw new Exception("팀 생성 후 멤버 추가에 실패했습니다.");
            }

            conn.commit();
            return teamId;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rbEx) {
                    rbEx.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException clEx) {
                    clEx.printStackTrace();
                }
            }
        }
    }

    /**
     * 멤버 초대: 이메일 조회 후 즉시 addMember (단일 삽입이므로 트랜잭션 생략 가능)
     */
    public boolean inviteMember(int teamId, UserDto dto) throws Exception {
        Connection conn = null;
        try {
            conn = ConnectionManager.getConnection();
            conn.setAutoCommit(false);

            // 1) 이메일로 사용자 ID 조회 (트랜잭션 내에서 수행)
            Integer userId = dao.findUserIdByEmail(dto.getEmail());
            if (userId == null) {
                conn.rollback();
                throw new Exception("해당 이메일 사용자가 존재하지 않음");
            }

            // 2) 팀에 멤버로 추가 (트랜잭션 내에서 수행)
            boolean added = dao.addMember(conn, teamId, userId);
            if (!added) {
                conn.rollback();
                return false;
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rbEx) {
                    rbEx.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException clEx) {
                    clEx.printStackTrace();
                }
            }
        }
    }

    /**
     * 팀장 권한 위임과 본인 탈퇴를 하나의 트랜잭션으로 묶음
     */
    public boolean delegateAndLeave(int teamId, int leaderId, int newLeaderId) throws Exception {
        Connection conn = null;
        try {
            conn = ConnectionManager.getConnection();
            conn.setAutoCommit(false);

            // ① 현재 팀장 여부 확인
            if (!dao.isLeader( teamId, leaderId)) {
                conn.rollback();
                return false;
            }
            // ② 새 리더가 팀원인지 확인
            if (!dao.isMember(teamId, newLeaderId)) {
                conn.rollback();
                return false;
            }
            // ③ 권한 위임
            dao.updateLeader(conn, teamId, newLeaderId);
            // ④ 기존 팀장은 멤버에서 제거
            boolean removed = dao.removeMember(conn, teamId, leaderId);
            if (!removed) {
                conn.rollback();
                return false;
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rbEx) {
                    rbEx.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException clEx) {
                    clEx.printStackTrace();
                }
            }
        }
    }

    /**
     * 일반 멤버 탈퇴
     */
    public boolean leaveTeam(int teamId, int userId) throws Exception {
        Connection conn = null;
        try {
            conn = ConnectionManager.getConnection();
            conn.setAutoCommit(false);

            // 1) 팀장 여부 및 마감일 조회 (트랜잭션 내에서 수행)
            boolean isLeader = dao.isLeader( teamId, userId);
            LocalDate deadline = dao.getDeadline( teamId);

            // 2) 조건 검사
            if (!isLeader && LocalDate.now().isBefore(deadline)) {
                System.out.println("마감일 이전 팀원 탈퇴 불가");
                conn.rollback();
                return false;
            }
            if (isLeader) {
                System.out.println("권한 위임 이후 탈퇴 가능");
                conn.rollback();
                return false;
            }

            // 3) 멤버 탈퇴 (DELETE)
            boolean removed = dao.removeMember(conn, teamId, userId);
            if (!removed) {
                conn.rollback();
                return false;
            }

            // 4) 커밋
            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rbEx) {
                    rbEx.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException clEx) {
                    clEx.printStackTrace();
                }
            }
        }
    }

}
