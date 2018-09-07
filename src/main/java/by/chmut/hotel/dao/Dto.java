package by.chmut.hotel.dao;

import by.chmut.hotel.bean.dto.RoomDto;

import java.time.LocalDate;
import java.util.List;

public interface Dto {

    List<RoomDto> getAllRoomsWhereCheckInOrCheckOutEqualsDate(LocalDate date) throws DAOException;
}
