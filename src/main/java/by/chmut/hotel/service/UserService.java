package by.chmut.hotel.service;

import by.chmut.hotel.bean.User;

import javax.servlet.http.Cookie;

public interface UserService {

    User getUser(String login) throws ServiceException;
    User getUser(Cookie cookie) throws ServiceException;
    User addUser(String login, String password, String name, String lastName, String email, String phone, String country,
                        String city, String address, String zip) throws ServiceException;

    Cookie addRememberMe(User user) throws ServiceException;
}
