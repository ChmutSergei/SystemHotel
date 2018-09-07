package by.chmut.hotel.dao;


import by.chmut.hotel.bean.Reservation;

import java.io.Serializable;
import java.util.List;

public interface ReservationDao extends Dao<Reservation>  {

    List<Reservation> getByUserId (Serializable userId) throws DAOException;
}

