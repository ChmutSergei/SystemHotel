package by.chmut.hotel.service;

import by.chmut.hotel.bean.Reservation;
import by.chmut.hotel.bean.Room;
import by.chmut.hotel.bean.User;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface ReservationService {

    Reservation save(Reservation reservation);

    Reservation get(Serializable id);

    void update(Reservation reservation);

    int delete(Serializable id);

    List<Reservation> getByUserId(Serializable userId);

    List<Room> getPaidRoomsIfUserHasThem(User user) throws SQLException;

}
