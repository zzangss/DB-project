package DB2025Team08;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 데이터베이스 연결을 관리하는 클래스.
 */
public class ConnectionManager {
    /**
     * MySQL JDBC 드라이버 클래스 이름.
     */
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * 연결할 데이터베이스 URL.
     */
    private static final String DB_URL =
        "jdbc:mysql://localhost:3306/DB2025Team08"
      + "?serverTimezone=Asia/Seoul&useSSL=false"
      + "&allowPublicKeyRetrieval=true";

    /**
     * 데이터베이스 사용자 이름.
     */
    private static final String USER = "DB2025Team08";

    /**
     * 데이터베이스 비밀번호.
     */
    private static final String PASS = "DB2025Team08";

    /**
     * JDBC 드라이버 로드를 한 번만 수행.
     */
    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("JDBC Driver 로드 실패", e);
        }
    }

    /**
     * 데이터베이스 연결을 반환.
     *
     * @return Connection 객체
     * @throws SQLException 연결에 실패하면 예외 발생
     * @implNote 호출한 쪽에서 close() 처리 필요
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        return conn;
    }
}
