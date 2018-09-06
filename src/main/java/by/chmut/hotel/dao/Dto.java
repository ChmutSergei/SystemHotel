package by.chmut.hotel.dao;

import by.chmut.hotel.bean.dto.RoomDto;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface Dto {

    List<RoomDto> getRoomWithCheckInOrDepartureForThisDay(LocalDate date) throws SQLException;
}
