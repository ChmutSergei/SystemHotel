package by.chmut.hotel.service;

import by.chmut.hotel.bean.User;

public interface UserService {

    User getUserByLogin(String login) throws ServiceException;
    User addUser(String login, String password, String name, String lastName, String email, String phone, String country,
                        String city, String address, String zip) throws ServiceException;
}
