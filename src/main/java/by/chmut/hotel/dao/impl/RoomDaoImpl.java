package by.chmut.hotel.dao.impl;

import by.chmut.hotel.dao.AbstractDao;
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

public class RoomDaoImpl extends AbstractDao implements RoomDao {

    private static final String selectById = "SELECT * FROM Rooms WHERE id=?";
    private static final String selectAllRoom = "SELECT * FROM Rooms";
    private static final String selectOnDateAndBedType = "SELECT room_id,roomNumber, type, bedType, price, description " +
            "FROM Rooms JOIN Reservation ON Rooms.id = Reservation.room_id WHERE bedType=? AND ((checkOut<=?)|(checkIn>=?))";
    private static final String addRoom = "INSERT INTO Rooms (roomNumber, type, bedType, price, checkIn, checkOut, description) " +
            "VALUES (?,?,?,?, now(), now(),?)";
    private static final String updateRoom = "UPDATE Rooms SET type=?, bedType=?, price=?," +
            " description=? WHERE roomNumber=?";

    private static final String deleteRoom = "DELETE FROM Rooms WHERE id=?";

    private Room setRoomFromResultSet(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setId(rs.getInt(1));
        room.setRoomNumber(rs.getInt(2));
        room.setType(rs.getString(3));
        room.setBedType(rs.getInt(4));
        room.setPrice(rs.getDouble(5));
//        room.setCheckIn(rs.getDate(6).toLocalDate());
//        room.setCheckOut(rs.getDate(7).toLocalDate());
        room.setDescription(rs.getString(6));
        return room;
    }
    public List<Room> getAllRoom() throws SQLException {
        List<Room> list = new ArrayList<>();
        PreparedStatement psGetAll = prepareStatement(selectAllRoom);
        ResultSet rs = psGetAll.executeQuery();
        while (rs.next()) {
            list.add(setRoomFromResultSet(rs));
        }
        close(rs);
        return list;
    }
    public List<Room> getRoomOnDateAndBedType(int bedType, LocalDate checkIn, LocalDate checkOut) throws SQLException{
        List<Room> list = new ArrayList<>();
        PreparedStatement psSearchRoom = prepareStatement(selectOnDateAndBedType);
        psSearchRoom.setInt(1,bedType);
        psSearchRoom.setDate(2,java.sql.Date.valueOf(checkIn));
        psSearchRoom.setDate(3,java.sql.Date.valueOf(checkOut));
        ResultSet rs = psSearchRoom.executeQuery();
        while (rs.next()) {
            list.add(setRoomFromResultSet(rs));
        }
        close(rs);
        return list;
    }

    @Override
    public Room save(Room room) throws SQLException {
        PreparedStatement psSave = prepareStatement(addRoom,Statement.RETURN_GENERATED_KEYS);
        psSave.setInt(1,room.getRoomNumber());
        psSave.setString(2,room.getType());
        psSave.setInt(3,room.getBedType());
        psSave.setDouble(4,room.getPrice());
        psSave.setString(5,room.getDescription());
        psSave.executeUpdate();
        ResultSet rs = psSave.getGeneratedKeys();
        if (rs.next()) {
           room.setId(rs.getInt(1));
        }
        close(rs);
        return room;
    }

    @Override
    public Room get(Serializable id) throws SQLException {
        PreparedStatement psGet = prepareStatement(selectById);
        psGet.setInt(1, (int)id);
        ResultSet rs = psGet.executeQuery();
        if (rs.next()) {
            return setRoomFromResultSet(rs);
        }
        close(rs);
        return null;
    }

    @Override
    public void update(Room room) throws SQLException {
        PreparedStatement psUpdate = prepareStatement(updateRoom);
        psUpdate.setInt(5,room.getRoomNumber());
        psUpdate.setString(1,room.getType());
        psUpdate.setInt(2,room.getBedType());
        psUpdate.setDouble(3,room.getPrice());
//        psUpdate.setDate(4,java.sql.Date.valueOf(room.getCheckIn()));
//        psUpdate.setDate(5,java.sql.Date.valueOf(room.getCheckOut()));
        psUpdate.setString(4,room.getDescription());
        psUpdate.executeUpdate();
    }

    @Override
    public int delete(Serializable id) throws SQLException {
        PreparedStatement psDelete = prepareStatement(deleteRoom);
        psDelete.setInt(1,(int)id);
        return psDelete.executeUpdate();
    }


    
}
