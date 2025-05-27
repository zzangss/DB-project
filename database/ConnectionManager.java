package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL =
        "jdbc:mysql://localhost:3306/DB2025TeamTrack"
      + "?serverTimezone=Asia/Seoul&useSSL=false"
      + "&allowPublicKeyRetrieval=true";
    private static final String USER = "DB2025TeamTrack";
    private static final String PASS = "DB2025TeamTrack";

    // 드라이버 로드는 static 블록에서 한 번만
    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("JDBC Driver 로드 실패", e);
        }
    }

    /** 호출한 쪽에서 close() 해 주세요. */
    public static Connection getConnection() throws SQLException {
        //System.out.println("Connecting to database...");
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
       // System.out.println("✅ DB 연결 성공!");
        return conn;
    }
}
