package by.chmut.hotel.dao;

import by.chmut.hotel.bean.User;

public interface UserDao extends Dao<User> {

    User getUserByLogin(String login) throws DAOException;

}
