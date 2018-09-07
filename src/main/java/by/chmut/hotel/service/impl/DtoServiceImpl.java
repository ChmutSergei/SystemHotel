package by.chmut.hotel.service.impl;

import by.chmut.hotel.bean.dto.RoomDto;
import by.chmut.hotel.dao.DAOException;
import by.chmut.hotel.dao.DAOFactory;
import by.chmut.hotel.service.DtoService;
import by.chmut.hotel.service.ServiceException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class DtoServiceImpl extends AbstractService implements DtoService {

    private DAOFactory factory = DAOFactory.getInstance();

    @Override
    public List<RoomDto> getRoomWithCheckInOrDepartureForThisDay(LocalDate date) throws ServiceException {

        List<RoomDto> result = Collections.emptyList();

        try {
            startTransaction();
            result = factory.getClientDto().getAllRoomsWhereCheckInOrCheckOutEqualsDate(date);
            commit();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(),e);
        }

        return result;
    }
}
