package by.chmut.hotel.dao;

import by.chmut.hotel.bean.dto.ClientDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface Dto {

    List<ClientDTO> getClientInfoOnDay(LocalDate date) throws SQLException;
}
