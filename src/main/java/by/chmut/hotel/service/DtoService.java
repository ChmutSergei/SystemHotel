package by.chmut.hotel.service;

import by.chmut.hotel.bean.dto.ClientDTO;

import java.time.LocalDate;
import java.util.List;

public interface DtoService {

    List<ClientDTO> getClientInfoOnDay (LocalDate date);
}
