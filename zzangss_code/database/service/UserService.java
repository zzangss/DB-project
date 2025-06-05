package database.service;

import database.dao.*;
import database.dto.*;
import database.model.User;

import java.sql.SQLException;

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
        if (userDao.findByEmail(dto.getEmail()) != null) {
            return false; // 이미 존재하는 이메일
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setName(dto.getName());
        user.setRole(dto.getRole());

        userDao.create(user);
        return true;
    }
}
