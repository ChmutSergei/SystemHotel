package by.chmut.hotel.service;

import by.chmut.hotel.bean.User;

public interface UserService {

    public User getUserByLogin(String login);
    public User addUser(String login, String password, String name, String lastName, String email, String phone, String country,
                        String city, String address, String zip);
}
