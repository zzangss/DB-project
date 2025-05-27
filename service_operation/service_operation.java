package service_operation;


import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class service_operation{
	
	static final String JDBC_URL="jdbc:mysql://localhost:3306/DB2025TeamTrack";
	static final String USER="root";
	static final String PASS="KJSoon0323@";
	
	public static void main(String[] args) {
		try(Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASS)){
			checkDueTomorrow(conn);
			updateTaskStatuses(conn);
			System.out.println("시스템 정상 종료.");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		System.exit(0); //시스템 종료
	}
	
	public static void checkDueTomorrow(Connection conn) throws SQLException{
		String query = "SELECT title, due_date FROM DB2025_task WHERE DATEDIFF(due_date,CURDATE())=1";
		try(Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)){
			while(rs.next()) {
				String title = rs.getString("title");
				System.out.println("내일'"+ title + "'과제가 마갑됩니다!");
			}
		}
	}
	
	//과제 제출 시 갱신됨
	  public static void updateTaskStatuses(Connection conn) throws SQLException{
		String query = "SELECT task_id, due_date, status FROM DB2025_task";
		try(
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)
						){
			while(rs.next()) {
				int id = rs.getInt("task_id");
				LocalDate dueDate = rs.getDate("due_date").toLocalDate();
				String status = rs.getString("status");
				LocalDate today = LocalDate.now();
				long daysSinceDue = ChronoUnit.DAYS.between(dueDate, today);
				
				String newStatus = null;
				if (status.equals("진행 중")) {
	                if (!today.isAfter(dueDate)) {
	                    newStatus = "완료";  // 기한 내 제출
	                } else if (daysSinceDue <= 3) {
	                    newStatus = "지각 제출";  // 마감 초과지만 3일 이내
	                } else {
	                    newStatus = "미제출";  // 마감 초과 3일 이상
	                }
	            }
				
				if(newStatus != null) {
					updateStatus(conn, id, newStatus);
				}
			}
		}
	}
	
	public static void updateStatus(Connection conn, int taskId, String newStatus) throws SQLException{
		String update="UPDATE DB2025_task SET status=? WHERE task_id=?";
		try(PreparedStatement ps = conn.prepareStatement(update)){
			ps.setString(1,newStatus);
			ps.setInt(2, taskId);
			ps.executeUpdate();
			System.out.println("과제 ID"+ taskId + "상태를'" + newStatus + "'로 변경했습니다.");
		}
	}
}