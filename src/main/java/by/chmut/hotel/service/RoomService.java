package by.chmut.hotel.service;

import by.chmut.hotel.bean.Room;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public interface RoomService {

    Room save(Room room) throws ServiceException;

    Room get(Serializable id) throws ServiceException;

    void update(Room room) throws ServiceException;

    int delete(Serializable id) throws ServiceException;

    List<Room> getAllRoom() throws ServiceException;

    List<Room> getAvailableRoom(int bedType, LocalDate checkIn, LocalDate checkOut) throws ServiceException;
}
