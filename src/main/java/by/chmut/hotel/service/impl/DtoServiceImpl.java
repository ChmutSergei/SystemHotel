package by.chmut.hotel.service.impl;

import by.chmut.hotel.bean.dto.ClientDTO;
import by.chmut.hotel.dao.DAOFactory;
import by.chmut.hotel.service.DtoService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DtoServiceImpl extends AbstractService implements DtoService {

    private DAOFactory factory = DAOFactory.getInstance();

    @Override
    public List<ClientDTO> getClientInfoOnDay(LocalDate date) {
        try {
            startTransaction();
            List<ClientDTO> result = factory.getClientDto().getClientInfoOnDay(date);
            commit();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
}
