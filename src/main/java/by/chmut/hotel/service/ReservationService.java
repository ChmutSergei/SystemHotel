package by.chmut.hotel.service;

import by.chmut.hotel.bean.Reservation;
import by.chmut.hotel.bean.Room;
import by.chmut.hotel.bean.User;

import java.io.Serializable;
import java.util.List;

public interface ReservationService {

    Reservation save(Reservation reservation) throws ServiceException;

    Reservation get(Serializable id) throws ServiceException;

    void update(Reservation reservation) throws ServiceException;

    int delete(Serializable id) throws ServiceException;

    List<Reservation> getByUserId(Serializable userId) throws ServiceException;

    List<Room> getPaidRoomsIfUserHasThem(User user) throws ServiceException;

    int deleteTemporaryReservation(int userId, Room room) throws ServiceException;

    void setPaidStatus(int userId, Room room) throws ServiceException;
}
