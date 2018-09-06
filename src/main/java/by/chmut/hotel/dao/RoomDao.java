package by.chmut.hotel.dao;

import by.chmut.hotel.bean.Room;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface RoomDao extends Dao<Room>{

    public List<Room> getAllRoom() throws SQLException;

    public List<Room> getRoomOnDateAndBedType(int bedType, LocalDate checkIn, LocalDate checkOut) throws SQLException;

}
