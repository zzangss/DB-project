package DB2025Team08.dao;

import DB2025Team08.model.*;
import DB2025Team08.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 기여도 데이터를 조회하는 DAO 클래스.
 */
public class ContributionDao {

    /**
     * 팀 ID로 팀원 기여도 목록을 조회한다.
     *
     * @param teamId 조회할 팀 ID
     * @return 팀원 기여도 목록
     * @throws SQLException DB 조회 중 오류 발생 시
     */
    public List<Contribution> findByTeamId(int teamId) throws SQLException {
        List<Contribution> result = new ArrayList<>();
        String sql = "SELECT team_id, name, raw_score, percenetage "
        		+ "FROM DB2025_view_contribution_summary "
        		+ "WHERE team_id = ? "
        		+ "ORDER BY percenetage DESC";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teamId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Contribution cs = new Contribution();
                cs.setTeamId(rs.getInt("team_id"));
                cs.setName(rs.getString("name"));
                cs.setRawScore(rs.getDouble("raw_score"));
                cs.setPercentage(rs.getDouble("percenetage"));
                result.add(cs);
            }
        }
        return result;
    }

    /**
     * 사용자 정보를 기준으로 팀별 기여도 목록을 조회한다.
     *
     * @param user 조회할 사용자 객체
     * @return 사용자 기여도 목록
     * @throws SQLException DB 조회 중 오류 발생 시
     */
    public List<Contribution> findByUser(User user) throws SQLException {
        List<Contribution> result = new ArrayList<>();
        String sql = "SELECT team_id, name, raw_score, percenetage "
        		+ "FROM DB2025_view_contribution_summary "
        		+ "WHERE name = ? "
        		+ "ORDER BY name DESC";

        try (Connection conn = ConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Contribution cs = new Contribution();
                cs.setTeamId(rs.getInt("team_id"));
                cs.setName(rs.getString("name"));
                cs.setRawScore(rs.getDouble("raw_score"));
                cs.setPercentage(rs.getDouble("percenetage"));
                result.add(cs);
            }
        }
        return result;
    }

}
