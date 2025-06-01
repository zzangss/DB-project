package database.service;

import database.model.Contribution;
import database.model.RoleAssignment;
import database.model.User;
import database.dao.ContributionDao;
import database.dao.RoleAssignmentDao;
import database.dao.UserDao;

import java.sql.SQLException;
import java.util.*;

public class ContributionService {

    private static ContributionDao contributionDao = new ContributionDao();
    private static RoleAssignmentDao roleDao = new RoleAssignmentDao();
    private static UserDao userDao = new UserDao();

    public static void analyzeAndDisplayContribution(int teamId) throws SQLException {
        List<Contribution> contributions = contributionDao.findByTeamId(teamId);
        Map<Integer, RoleAssignment> roles = roleDao.findRolesByTeamId(teamId);
        Map<Integer, User> users = userDao.findUsersByTeamId(teamId);

        Map<Integer, Double> scoreMap = new HashMap<>();

        // 점수 계산
        for (Contribution c : contributions) {
            double roleBonus = 0.0;
            RoleAssignment role = roles.get(c.getUserId());
            if (role != null && (role.getRoleName().equals("팀장") || role.getRoleName().equals("총 취합자"))) {
                roleBonus = 1.0;
            }

            double score = c.getSubmittedCount() * 1 + c.getAttendedCount() * 0.5 + roleBonus;
            scoreMap.put(c.getUserId(), score);
        }

        // 최고 점수 찾기
        double maxScore = scoreMap.values().stream().max(Double::compare).orElse(1.0);

        // 시각화
        System.out.println("=== 팀 기여도 시각화 (백분율 기준) ===");
        for (Map.Entry<Integer, Double> entry : scoreMap.entrySet()) {
            int userId = entry.getKey();
            double percentage = (entry.getValue() / maxScore) * 100;
            String bar = generateBar(percentage);
            String name = users.containsKey(userId) ? users.get(userId).getName() : "알 수 없음";
            System.out.printf("%s (%.1f%%): %s\n", name, percentage, bar);
        }
    }

    private static String generateBar(double percentage) {
        int length = (int) (percentage / 5); // 100% 기준 20칸
        String result = "";
        for(int i = length; i>0; i--) {
        	result += "█";
        }
        return result;
    }
}
