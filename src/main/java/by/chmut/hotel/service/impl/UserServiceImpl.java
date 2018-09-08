package by.chmut.hotel.service.impl;

import by.chmut.hotel.dao.DAOException;
import by.chmut.hotel.dao.DAOFactory;
import by.chmut.hotel.dao.UserDao;
import by.chmut.hotel.bean.User;
import by.chmut.hotel.service.ServiceException;
import by.chmut.hotel.service.UserService;

public class UserServiceImpl extends AbstractService implements UserService {

    private DAOFactory factory = DAOFactory.getInstance();

    private UserDao userDao = factory.getUserDao();

    @Override
    public User getUserByLogin(String login) throws ServiceException{
        try {
            startTransaction();
            User user = userDao.getUserByLogin(login);
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
            User user = userDao.getUserByLogin(login);
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

}
