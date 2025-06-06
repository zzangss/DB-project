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

/**
 * 팀 관련 비즈니스 로직을 처리하는 서비스 클래스.
 */
public class TeamService {
    /**
     * 팀 DAO 객체
     */
    private final TeamDao dao;

    /**
     * 기본 생성자.
     */
    public TeamService() {
        this.dao = new TeamDao();
    }

    /**
     * 팀 생성과 리더 멤버 추가를 하나의 트랜잭션으로 처리한다.
     *
     * @param dto 팀 생성 정보를 담은 DTO
     * @return 생성된 팀 ID
     * @throws Exception 생성 실패 시 예외 발생
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
     * 이메일로 조회한 사용자에게 팀 멤버로 초대한다.
     *
     * @param teamId 초대할 팀 ID
     * @param dto    초대할 사용자 정보를 담은 DTO
     * @return 초대 성공 시 true, 실패 시 false
     * @throws Exception 오류 발생 시 예외 발생
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
     * 팀장 권한을 위임하고 본인을 팀에서 탈퇴한다.
     *
     * @param teamId      팀 ID
     * @param leaderId    현재 팀장 ID
     * @param newLeaderId 새로 위임할 팀장 ID
     * @return 위임 및 탈퇴 성공 시 true, 실패 시 false
     * @throws Exception 오류 발생 시 예외 발생
     */
    public boolean delegateAndLeave(int teamId, int leaderId, int newLeaderId) throws Exception {
        Connection conn = null;
        try {
            conn = ConnectionManager.getConnection();
            conn.setAutoCommit(false);

            // ① 현재 팀장 여부 확인
            if (!dao.isLeader(teamId, leaderId)) {
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
     * 팀의 구성원 목록을 조회한다.
     *
     * @param teamId 조회할 팀 ID
     * @return 팀 구성원 목록
     * @throws Exception 오류 발생 시 예외 발생
     */
    public List<User> getMembersOfTeam(int teamId) throws Exception {
        return dao.getTeamMembersByTeamId(teamId);
    }

    /**
     * 일반 멤버를 팀에서 탈퇴시킨다.
     *
     * @param teamId 팀 ID
     * @param userId 탈퇴할 사용자 ID
     * @return 탈퇴 성공 시 true, 실패 시 false
     * @throws Exception 오류 발생 시 예외 발생
     */
    public boolean leaveTeam(int teamId, int userId) throws Exception {
        Connection conn = null;
        try {
            conn = ConnectionManager.getConnection();
            conn.setAutoCommit(false);

            // 1) 팀장 여부 및 마감일 조회 (트랜잭션 내에서 수행)
            boolean isLeader = dao.isLeader(teamId, userId);
            LocalDate deadline = dao.getDeadline(teamId);

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
