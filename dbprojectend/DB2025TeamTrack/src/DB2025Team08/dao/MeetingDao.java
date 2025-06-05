package DB2025Team08.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import DB2025Team08.ConnectionManager;
import DB2025Team08.dto.MeetingDto;

/**
 * 회의 데이터를 처리하는 DAO 클래스.
 */
public class MeetingDao {

    /**
     * 회의 정보를 삽입한다.
     *
     * @param conn 데이터베이스 연결 객체
     * @param meeting 회의 DTO
     * @return 삽입 성공 여부
     * @throws SQLException SQL 실행 오류
     */
    public boolean insertMeeting(Connection conn, MeetingDto meeting) throws SQLException {
        String sql = "INSERT INTO DB2025_meeting (team_id, meeting_date, content, decision) VALUES (?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, meeting.getTeamId());
            ps.setDate(2, Date.valueOf(meeting.getMeetingDate()));
            ps.setString(3, meeting.getContent());
            ps.setString(4, meeting.getDecision());
            int affected = ps.executeUpdate();
            return affected > 0;
        }
    }

    /**
     * 팀장 ID를 조회한다.
     *
     * @param teamId 팀 ID
     * @return 팀장 사용자 ID, 없으면 -1 반환
     * @throws SQLException SQL 실행 오류
     */
    public int getTeamLeaderId(int teamId) throws SQLException {
        String sql = "SELECT leader_id FROM DB2025_team WHERE team_id = ?";
        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teamId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("leader_id");
                } else {
                    return -1;
                }
            }
        }
    }

    /**
     * 팀 ID로 회의 목록을 조회한다.
     *
     * @param teamId 팀 ID
     * @return 회의 DTO 목록
     * @throws SQLException SQL 실행 오류
     */
    public List<MeetingDto> getMeetingsByTeamId(int teamId) throws SQLException {
        List<MeetingDto> meetings = new ArrayList<>();
        String sql = "SELECT team_id, meeting_date, content, decision FROM DB2025_meeting WHERE team_id = ? ORDER BY meeting_date DESC";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, teamId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MeetingDto dto = new MeetingDto(
                        rs.getInt("team_id"),
                        rs.getDate("meeting_date").toLocalDate(),
                        rs.getString("content"),
                        rs.getString("decision")
                    );
                    meetings.add(dto);
                }
            }
        }

        return meetings;
    }

    /**
     * 팀 ID로 회의 목록을 조회한다.
     *
     * @param teamId 팀 ID
     * @return 회의 DTO 목록
     * @throws SQLException SQL 실행 오류
     */
    public List<MeetingDto> findMeetingsByTeamId(int teamId) throws SQLException {
        List<MeetingDto> list = new ArrayList<>();
        String sql = "SELECT meeting_date, content, decision FROM DB2025_meeting WHERE team_id = ? ORDER BY meeting_date DESC";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teamId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    MeetingDto dto = new MeetingDto(
                        teamId,
                        rs.getDate("meeting_date").toLocalDate(),
                        rs.getString("content"),
                        rs.getString("decision")
                    );
                    list.add(dto);
                }
            }
        }
        return list;
    }
}
