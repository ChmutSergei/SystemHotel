package by.chmut.hotel.dao;

import by.chmut.hotel.bean.User;

import java.sql.SQLException;

public interface UserDao extends Dao<User> {
    public User getUserByLogin(String login) throws SQLException;
}
