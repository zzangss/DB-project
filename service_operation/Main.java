import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import dao.TaskDAO;

public class Main {
	
	static final String JDBC_URL="jdbc:mysql://localhost:3306/DB2025Team08";
	static final String USER="DB2025Team08";
	static final String PASS="DB2025Team08";
	
	
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASS)) {
            TaskDAO dao = new TaskDAO(conn);
            TaskService service = new TaskService(dao);

            service.notifyDueTasksAndUpdateStatuses(); // 핵심 로직 실행

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}