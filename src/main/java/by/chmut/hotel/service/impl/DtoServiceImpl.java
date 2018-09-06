package by.chmut.hotel.service.impl;

import by.chmut.hotel.bean.dto.RoomDto;
import by.chmut.hotel.dao.DAOFactory;
import by.chmut.hotel.service.DtoService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DtoServiceImpl extends AbstractService implements DtoService {

    private DAOFactory factory = DAOFactory.getInstance();

    @Override
    public List<RoomDto> getRoomWithCheckInOrDepartureForThisDay(LocalDate date) {
        try {
            startTransaction();
            List<RoomDto> result = factory.getClientDto().getRoomWithCheckInOrDepartureForThisDay(date);
            commit();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
}
