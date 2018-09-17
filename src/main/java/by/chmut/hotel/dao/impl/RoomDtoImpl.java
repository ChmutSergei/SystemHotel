package by.chmut.hotel.dao.impl;

import by.chmut.hotel.bean.dto.RoomDto;
import by.chmut.hotel.dao.AbstractDao;
import by.chmut.hotel.dao.DAOException;
import by.chmut.hotel.dao.Dto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomDtoImpl extends AbstractDao implements Dto {

    String selectData = "SELECT roomNumber,bedType,checkIn,checkOut, name,lastname,telephone,city,price FROM " +
            "Reservation JOIN Users U on user_id = U.id JOIN Rooms RememberMeTokenImpl on room_id = RememberMeTokenImpl.id JOIN Contacts C on U.contact_id = C.id " +
            "WHERE  checkIn=? OR checkOut=?";

    public List<RoomDto> getAllRoomsWhereCheckInOrCheckOutEqualsDate(LocalDate date) throws DAOException {
        List<RoomDto> list = new ArrayList<>();
        try {
            PreparedStatement psSearchRoom = prepareStatement(selectData);
            psSearchRoom.setDate(1, java.sql.Date.valueOf(date));
            psSearchRoom.setDate(2, java.sql.Date.valueOf(date));
            ResultSet rs = psSearchRoom.executeQuery();
            while (rs.next()) {
                list.add(setDtoFromResultSet(rs));
            }
            close(rs);
        } catch (SQLException e) {
            throw new DAOException("Error with get Rooms by check in or check out equals date",e);
        }
        return list;
    }
    private RoomDto setDtoFromResultSet(ResultSet rs) throws DAOException {
        RoomDto result = new RoomDto();
        try {
            result.setRoomNumber(rs.getInt(1));
            result.setBedType(rs.getInt(2));
            result.setCheckIn(rs.getDate(3).toLocalDate());
            result.setCheckOut(rs.getDate(4).toLocalDate());
            result.setName(rs.getString(5));
            result.setLastname(rs.getString(6));
            result.setTelephone(rs.getString(7));
            result.setCity(rs.getString(8));
            result.setPrice(rs.getDouble(9));
        } catch (SQLException e) {
            throw new DAOException("Error with set from ResultSet", e);
        }
        return result;
    }
}
