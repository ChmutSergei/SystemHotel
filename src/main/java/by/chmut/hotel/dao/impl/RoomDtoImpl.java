package by.chmut.hotel.dao.impl;

import by.chmut.hotel.bean.dto.RoomDto;
import by.chmut.hotel.dao.AbstractDao;
import by.chmut.hotel.dao.Dto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomDtoImpl extends AbstractDao implements Dto {

    String selectData = "SELECT roomNumber,bedType,checkIn,checkOut, name,lastname,telephone,city,price FROM " +
            "Reservation JOIN Users U on user_id = U.id JOIN Rooms R on room_id = R.id JOIN Contacts C on U.contact_id = C.id " +
            "WHERE  checkIn=? OR checkOut=?";

    public List<RoomDto> getRoomWithCheckInOrDepartureForThisDay(LocalDate date) throws SQLException {
        List<RoomDto> list = new ArrayList<>();
        PreparedStatement psSearchRoom = prepareStatement(selectData);
        psSearchRoom.setDate(1,java.sql.Date.valueOf(date));
        psSearchRoom.setDate(2,java.sql.Date.valueOf(date));
        ResultSet rs = psSearchRoom.executeQuery();
        while (rs.next()) {
            list.add(setDtoFromResultSet(rs));
        }
        close(rs);
        return list;
    }
    private RoomDto setDtoFromResultSet(ResultSet rs) throws SQLException {
        RoomDto data = new RoomDto();
        data.setRoomNumber(rs.getInt(1));
        data.setBedType(rs.getInt(2));
        data.setCheckIn(rs.getDate(3).toLocalDate());
        data.setCheckOut(rs.getDate(4).toLocalDate());
        data.setName(rs.getString(5));
        data.setLastname(rs.getString(6));
        data.setTelephone(rs.getString(7));
        data.setCity(rs.getString(8));
        data.setPrice(rs.getDouble(9));
        return data;
    }
}
