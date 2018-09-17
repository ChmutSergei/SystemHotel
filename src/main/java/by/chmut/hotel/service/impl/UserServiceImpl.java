package by.chmut.hotel.service.impl;

import by.chmut.hotel.dao.DAOException;
import by.chmut.hotel.dao.DAOFactory;
import by.chmut.hotel.dao.UserDao;
import by.chmut.hotel.bean.User;
import by.chmut.hotel.service.ServiceException;
import by.chmut.hotel.service.UserService;

import javax.servlet.http.Cookie;

public class UserServiceImpl extends AbstractService implements UserService {

    private DAOFactory factory = DAOFactory.getInstance();

    private UserDao userDao = factory.getUserDao();

    @Override
    public User getUser(String login) throws ServiceException{
        try {
            startTransaction();
            User user = userDao.getUser(login);
            commit();
            return user;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getUser(Cookie cookie) throws ServiceException {
        try {
            startTransaction();
            User user = userDao.getUser(cookie);
            commit();
            return user;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User addUser(String login, String password, String name, String lastName, String email, String phone, String country,
                        String city, String address, String zip) throws ServiceException {
        try {
            startTransaction();
            User user = userDao.getUser(login);
            commit();
            if (user.getId() == 0) {
                startTransaction();
                user = new User(login, password, name, lastName, "user", email, phone, country, city, address, zip);
                user = userDao.save(user);
                commit();
            } else {
                user = null;
            }
            return user;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Cookie addRememberMe(User user) throws ServiceException {
        try {
            startTransaction();
            Cookie cookie = factory.getUserDao().addToken(user);
            commit();
            return cookie;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

}
