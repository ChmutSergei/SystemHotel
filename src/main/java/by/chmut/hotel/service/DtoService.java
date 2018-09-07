package by.chmut.hotel.service;

import by.chmut.hotel.bean.dto.RoomDto;

import java.time.LocalDate;
import java.util.List;

public interface DtoService {

    List<RoomDto> getRoomWithCheckInOrDepartureForThisDay(LocalDate date) throws ServiceException;
}
