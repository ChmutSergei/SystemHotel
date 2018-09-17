package by.chmut.hotel.dao.impl;

import by.chmut.hotel.dao.AbstractDao;
import by.chmut.hotel.dao.DAOException;
import by.chmut.hotel.dao.RoomDao;
import by.chmut.hotel.bean.Room;


import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static by.chmut.hotel.controller.command.impl.constant.Constants.QUANTITY_MINUTES_FOR_PAYMENT;

public class RoomDaoImpl extends AbstractDao implements RoomDao {

    private static final String selectById = "SELECT * FROM Rooms WHERE id=?";

    private static final String selectAllRoom = "SELECT * FROM Rooms";

    private static final String addRoom = "INSERT INTO Rooms (roomNumber, type, bedType, price, checkIn, checkOut, description) " +
            "VALUES (?,?,?,?, now(), now(),?)";

    private static final String updateRoom = "UPDATE Rooms SET type=?, bedType=?, price=?," +
            " description=? WHERE roomNumber=?";

    private static final String deleteRoom = "DELETE FROM Rooms WHERE id=?";

    private static final String getAvailableRoom ="SELECT * FROM Rooms WHERE bedType=? AND Rooms.id NOT IN " +
            "(SELECT RememberMeTokenImpl.id FROM Rooms RememberMeTokenImpl JOIN Reservation RS ON RememberMeTokenImpl.id = RS.room_id WHERE (payment = 1  AND " +
            "(checkOut>?) AND (checkIn<?)) OR (payment = 0 AND (SELECT TIMESTAMPDIFF (MINUTE,date,now()))<? AND checkOut>? AND checkIn<?))";

    private Room setRoomFromResultSet(ResultSet rs) throws DAOException {
        Room room = new Room();
        try {
            room.setId(rs.getInt(1));
            room.setRoomNumber(rs.getInt(2));
            room.setType(rs.getString(3));
            room.setBedType(rs.getInt(4));
            room.setPrice(rs.getLong(5));
            room.setDescription(rs.getString(6));
        } catch (SQLException e) {
            throw new DAOException("Error with set from ResultSet",e);
        }
        return room;
    }
    public List<Room> getAllRoom() throws DAOException {
        List<Room> list = new ArrayList<>();
        try {
            PreparedStatement psGetAll = prepareStatement(selectAllRoom);
            ResultSet rs = psGetAll.executeQuery();
            while (rs.next()) {
                list.add(setRoomFromResultSet(rs));
            }
            close(rs);
        } catch (SQLException e) {
            throw new DAOException("Error with get all Rooms",e);
        }
        return list;
    }

    @Override
    public List<Room> getAvailableRoom(int bedType, LocalDate checkIn, LocalDate checkOut) throws DAOException {
        List<Room> list = new ArrayList<>();
        try {
            PreparedStatement psSearchRoom = prepareStatement(getAvailableRoom);
            psSearchRoom.setInt(1, bedType);
            psSearchRoom.setDate(2, java.sql.Date.valueOf(checkIn));
            psSearchRoom.setDate(3, java.sql.Date.valueOf(checkOut));
            psSearchRoom.setInt(4, QUANTITY_MINUTES_FOR_PAYMENT);
            psSearchRoom.setDate(5, java.sql.Date.valueOf(checkIn));
            psSearchRoom.setDate(6, java.sql.Date.valueOf(checkOut));
            ResultSet rs = psSearchRoom.executeQuery();
            while (rs.next()) {
                list.add(setRoomFromResultSet(rs));
            }
            close(rs);
        } catch (SQLException e) {
            throw new DAOException("Error with get available Rooms",e);
        }
        return list;
    }

    @Override
    public Room save(Room room) throws DAOException {
        try {
            PreparedStatement psSave = prepareStatement(addRoom, Statement.RETURN_GENERATED_KEYS);
            psSave.setInt(1, room.getRoomNumber());
            psSave.setString(2, room.getType());
            psSave.setInt(3, room.getBedType());
            psSave.setLong(4, room.getPrice());
            psSave.setString(5, room.getDescription());
            psSave.executeUpdate();
            ResultSet rs = psSave.getGeneratedKeys();
            if (rs.next()) {
                room.setId(rs.getInt(1));
            }
            close(rs);
        } catch (SQLException e) {
            throw new DAOException("Error with save Room", e);
        }
        return room;
    }

    @Override
    public Room get(Serializable id) throws DAOException {
        Room room = new Room();
        try {
            PreparedStatement psGet = prepareStatement(selectById);
            psGet.setInt(1, (int) id);
            ResultSet rs = psGet.executeQuery();
            if (rs.next()) {
                return setRoomFromResultSet(rs);
            }
            close(rs);
        } catch (SQLException e) {
            throw new DAOException("Error with get Room by Id",e);
        }
        return room;
    }

    @Override
    public void update(Room room) throws DAOException {
        try {
            PreparedStatement psUpdate = prepareStatement(updateRoom);
            psUpdate.setInt(5, room.getRoomNumber());
            psUpdate.setString(1, room.getType());
            psUpdate.setInt(2, room.getBedType());
            psUpdate.setLong(3, room.getPrice());
            psUpdate.setString(4, room.getDescription());
            psUpdate.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error with update Room",e);
        }
    }

    @Override
    public int delete(Serializable id) throws DAOException {
        try {
            PreparedStatement psDelete = prepareStatement(deleteRoom);
            psDelete.setInt(1, (int) id);
            return psDelete.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Error with delete Room",e);
        }
    }


    
}
