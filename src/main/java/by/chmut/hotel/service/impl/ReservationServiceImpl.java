package by.chmut.hotel.service.impl;

import by.chmut.hotel.bean.Room;
import by.chmut.hotel.bean.User;
import by.chmut.hotel.dao.DAOException;
import by.chmut.hotel.dao.DAOFactory;
import by.chmut.hotel.dao.ReservationDao;
import by.chmut.hotel.bean.Reservation;
import by.chmut.hotel.service.ReservationService;
import by.chmut.hotel.service.ServiceException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReservationServiceImpl extends AbstractService implements ReservationService {

    private DAOFactory factory = DAOFactory.getInstance();

    private ReservationDao reservationDao = factory.getReservationDao();


    @Override
    public Reservation save(Reservation reservation) throws ServiceException {
        try {
            startTransaction();
            reservation = reservationDao.save(reservation);
            commit();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return reservation;
    }

    @Override
    public Reservation get(Serializable id) throws ServiceException {
        try {
            startTransaction();
            Reservation reservation = reservationDao.get(id);
            commit();
            return reservation;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(Reservation reservation) throws ServiceException {
        try {
            startTransaction();
            reservationDao.update(reservation);
            commit();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int delete(Serializable id) throws ServiceException {
        try {
            startTransaction();
            int rows = reservationDao.delete(id);
            commit();
            return rows;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Reservation> getByUserId(Serializable userId) throws ServiceException {
        try {
            startTransaction();
            List<Reservation> reservations = reservationDao.getByUserId(userId);
            commit();
            return reservations;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Room> getPaidRoomsIfUserHasThem(User user) throws ServiceException{
        try {
            List<Reservation> lastReservations = reservationDao.getByUserId(user.getId());
            if (lastReservations.isEmpty()) {
                return Collections.emptyList();
            }
            List<Room> result = new ArrayList<>();
            for (Reservation reservation : lastReservations) {
                if (reservation.getPayment() == 1) {
                    Room room = factory.getRoomDao().get(reservation.getRoomId());
                    room.setCheckIn(reservation.getCheckIn());
                    room.setCheckOut(reservation.getCheckOut());
                    result.add(room);
                }
            }
            return result;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public int deleteTemporaryReservation(int userId, Room room) throws ServiceException {
        int rows = 0;
        try {
            startTransaction();
            rows = reservationDao.deleteTemporaryReservation(userId, room);
            commit();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return rows;
    }

    @Override
    public void setPaidStatus(int userId, Room room) throws ServiceException {
        try {
            startTransaction();
            reservationDao.setPaidStatus(userId,room);
            commit();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }


}
