package DB2025Team08.dao;

import DB2025Team08.model.*;
import DB2025Team08.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContributionDao {

    // 팀id를 매개변수로 사용. 팀원 전체의 기여도를 리스트로 반환한다.
    public List<Contribution> findByTeamId(int teamId) throws SQLException {
        List<Contribution> result = new ArrayList<>();
        String sql = "SELECT team_id, name, raw_score, percenetage "
        		+ "FROM DB2025_view_contribution_summary "
        		+ "WHERE team_id = ? "
        		+ "ORDER BY percenetage DESC";  // 백분율 순 정렬

        try(Connection conn = ConnectionManager.getConnection();
        	PreparedStatement ps = conn.prepareStatement(sql)){
        	ps.setInt(1, teamId);
        	
        	// ③ 실행 및 결과 매핑
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
    
    // user를 매개변수로 사용. 해당하는 유저의 팀별 기여도를 list로 반환.
    public List<Contribution> findByUser(User user) throws SQLException {
        List<Contribution> result = new ArrayList<>();
        String sql = "SELECT team_id, name, raw_score, percenetage "
        		+ "FROM DB2025_view_contribution_summary " //기여도를 자동으로 계산해주는 뷰 이용
        		+ "WHERE name = ? "
        		+ "ORDER BY name DESC";  // 이름 순 정렬

        try(Connection conn = ConnectionManager.getConnection();
        	PreparedStatement ps = conn.prepareStatement(sql)){
        	
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
