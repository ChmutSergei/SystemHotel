package by.chmut.hotel.service.impl;

import by.chmut.hotel.dao.DAOException;
import by.chmut.hotel.dao.DAOFactory;
import by.chmut.hotel.dao.RoomDao;
import by.chmut.hotel.bean.Room;
import by.chmut.hotel.service.RoomService;
import by.chmut.hotel.service.ServiceException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class RoomServiceImpl extends AbstractService implements RoomService {
    private DAOFactory factory = DAOFactory.getInstance();
    private RoomDao roomDao = factory.getRoomDao();

    @Override
    public Room save(Room room) throws ServiceException {
        try {
            startTransaction();
            room = roomDao.save(room);
            commit();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(),e);
        }
        return room;
    }

    @Override
    public Room get(Serializable id) throws ServiceException {
        try {
            startTransaction();
            Room room = roomDao.get(id);
            commit();
            return room;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    public void update(Room room) throws ServiceException {
        try {
            startTransaction();
            roomDao.update(room);
            commit();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    public int delete(Serializable id) throws ServiceException {
        try {
            startTransaction();
            int rows = roomDao.delete(id);
            commit();
            return rows;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    public List<Room> getRoomByDateAndBedType(int bedType, LocalDate checkIn, LocalDate checkOut) throws ServiceException {
        try {
            startTransaction();
            List<Room> room = roomDao.getRoomByDateAndBedType(bedType,checkIn,checkOut);
            commit();
            return room;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    public List<Room> getAllRoom() throws ServiceException {
        try {
            startTransaction();
            List<Room> room = roomDao.getAllRoom();
            commit();
            return room;
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

}
