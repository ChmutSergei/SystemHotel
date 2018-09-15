package by.chmut.hotel.dao.impl;

import by.chmut.hotel.bean.Room;
import by.chmut.hotel.dao.AbstractDao;
import by.chmut.hotel.dao.DAOException;
import by.chmut.hotel.dao.ReservationDao;
import by.chmut.hotel.bean.Reservation;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationDaoImpl extends AbstractDao implements ReservationDao {

    private static final String saveQuery = "INSERT INTO Reservation (user_id, room_id, checkIn, checkOut, date, payment) " +
            "VALUES (?,?,?,?,now(),0)";
    private static final String getQuery = "SELECT * FROM Reservation WHERE id=?";
    private static final String getQueryUserId = "SELECT * FROM Reservation WHERE user_id=?";
    private static final String deleteTempReservation = "DELETE FROM Reservation WHERE payment=0 AND user_id=? " +
            "AND room_id=? AND checkIn=? AND checkOut=?";
    private static final String updateQuery = "UPDATE Reservation SET room_id=? WHERE id=?";
    private static final String updatePayment = "UPDATE Reservation SET payment=1 WHERE user_id=? AND room_id=? " +
            "AND checkIn=? AND checkOut=?";
    private static final String deleteQuery = "DELETE FROM Reservation WHERE id=?";


    private Reservation setFromResultSet(ResultSet rs) throws DAOException {
        Reservation reservation = new Reservation();
        try {
            reservation.setId(rs.getInt(1));
            reservation.setUserId(rs.getInt(2));
            reservation.setRoomId(rs.getInt(3));
            reservation.setCheckIn(rs.getDate(4).toLocalDate());
            reservation.setCheckOut(rs.getDate(5).toLocalDate());
            reservation.setDateTime(rs.getTimestamp(6).toLocalDateTime());
            reservation.setPayment(rs.getInt(7));
        } catch (SQLException e) {
            throw new DAOException("Error with set params from ResultSet",e);
        }
        return reservation;
    }

    @Override
    public List<Reservation> getByUserId(Serializable userId) throws DAOException {
        List<Reservation> list = new ArrayList<>();
        try {
            PreparedStatement psGetByUserId = prepareStatement(getQueryUserId);
            psGetByUserId.setInt(1, (int) userId);
            ResultSet rs = psGetByUserId.executeQuery();
            while (rs.next()) {
                list.add(setFromResultSet(rs));
            }
            close(rs);
        } catch (SQLException e) {
            throw new DAOException("Error with get Reservation by UserID",e);
        }
        return list;
    }

    @Override
    public int deleteTemporaryReservation(int userId, Room room) throws DAOException {
        int rows;
        try {
            PreparedStatement psDeleteTemp = prepareStatement(deleteTempReservation);
            psDeleteTemp.setInt(1, userId);
            psDeleteTemp.setInt(2, room.getId());
            psDeleteTemp.setDate(3, java.sql.Date.valueOf(room.getCheckIn()));
            psDeleteTemp.setDate(4, java.sql.Date.valueOf(room.getCheckOut()));
            rows = psDeleteTemp.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error with delete temporary reservation",e);
        }
        return rows;
    }

    @Override
    public void setPaidStatus(int userId, Room room) throws DAOException {
        try {
            PreparedStatement psUpdatePayment = prepareStatement(updatePayment);
            psUpdatePayment.setInt(1, userId);
            psUpdatePayment.setInt(2, room.getId());
            psUpdatePayment.setDate(3, java.sql.Date.valueOf(room.getCheckIn()));
            psUpdatePayment.setDate(4, java.sql.Date.valueOf(room.getCheckOut()));
            psUpdatePayment.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error with set paid status for reservation",e);
        }

    }

    @Override
    public Reservation save(Reservation reservation) throws DAOException {
        try {
            PreparedStatement psReservation = prepareStatement(saveQuery, Statement.RETURN_GENERATED_KEYS);
            psReservation.setInt(1, reservation.getUserId());
            psReservation.setInt(2, reservation.getRoomId());
            psReservation.setDate(3, java.sql.Date.valueOf(reservation.getCheckIn()));
            psReservation.setDate(4, java.sql.Date.valueOf(reservation.getCheckOut()));
            psReservation.executeUpdate();
            ResultSet rs = psReservation.getGeneratedKeys();
            if (rs.next()) {
                reservation.setId(rs.getInt(1));
            }
            close(rs);
            reservation.setDateTime(LocalDateTime.now());
            reservation.setPayment(0); // set unpaid when save reservation
        } catch (SQLException e) {
            throw new DAOException("Error with save Reservation",e);
        }
        return reservation;
    }

    @Override
    public Reservation get(Serializable id) throws DAOException {
        try {
            PreparedStatement psGetReservation = prepareStatement(getQuery);
            psGetReservation.setInt(1, (int) id);
            ResultSet rs = psGetReservation.executeQuery();
            if (rs.next()) {
                return setFromResultSet(rs);
            }
        } catch (SQLException e) {
            throw new DAOException("Error with get Reservation by ID",e);
        }
        return new Reservation();
    }


    @Override
    public void update(Reservation reservation) throws DAOException {
        try {
            PreparedStatement psUpdateReservation = prepareStatement(updateQuery);
            psUpdateReservation.setInt(2, reservation.getId());
            psUpdateReservation.setInt(1, reservation.getRoomId());
            psUpdateReservation.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error with update Reservation",e);
        }

    }

    @Override
    public int delete(Serializable id) throws DAOException {
        try {
            PreparedStatement psDelete = prepareStatement(deleteQuery);
            psDelete.setInt(1, (int) id);
            return psDelete.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error with delete Reservation", e);
        }
    }


}
