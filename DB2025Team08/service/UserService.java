package DB2025Team08.service;

import java.sql.Connection;
import java.sql.SQLException;

import DB2025Team08.ConnectionManager;
import DB2025Team08.dao.*;
import DB2025Team08.dto.*;
import DB2025Team08.model.User;

public class UserService {
    private final static UserDao userDao = new UserDao();

    public static User login(LoginRequestDto loginDto) throws SQLException {
        User user = userDao.findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword());
        if (user != null && user.getPassword().equals(loginDto.getPassword())) {
            return user;
        }
        return null;
    }
    
    public static boolean register(UserDto dto) throws SQLException {
    	Connection conn = null;
    	
    	try {
    	        // ① 여기서 Connection을 열고(=트랜잭션 시작)
    	        conn = ConnectionManager.getConnection();
    	        conn.setAutoCommit(false);

    	        if (userDao.findByEmail(dto.getEmail()) != null) {
    	            return false; // 이미 존재하는 이메일
    	        }

    	        User user = new User();
    	        user.setEmail(dto.getEmail());
    	        user.setPassword(dto.getPassword());
    	        user.setName(dto.getName());
    	        user.setRole(dto.getRole());

    	        userDao.create(conn, user);
    	        
    	        conn.commit();
    	        
    	} catch (SQLException ex) {
            // 예외 발생 시 롤백
            if (conn != null) conn.rollback();
            throw ex;
        } finally {
            // 마지막에 Connection 닫기
            if (conn != null) conn.close();
        }        
        
        return true;
    }
}
