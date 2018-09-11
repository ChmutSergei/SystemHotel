package by.chmut.hotel.dao;

import by.chmut.hotel.bean.Room;

import java.time.LocalDate;
import java.util.List;

public interface RoomDao extends Dao<Room>{

    List<Room> getAllRoom() throws DAOException;

    List<Room> getAvailableRoom(int bedType, LocalDate checkIn, LocalDate checkOut) throws DAOException;

}
