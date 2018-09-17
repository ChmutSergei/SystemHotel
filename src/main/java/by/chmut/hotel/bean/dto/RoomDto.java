package by.chmut.hotel.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;


@AllArgsConstructor
@Data
@NoArgsConstructor

public class RoomDto {

    private int roomNumber;
    private int bedType;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String name;
    private String lastname;
    private String telephone;
    private String city;
    private double price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomDto roomDto = (RoomDto) o;
        return roomNumber == roomDto.roomNumber &&
                bedType == roomDto.bedType &&
                Double.compare(roomDto.price, price) == 0 &&
                Objects.equals(checkIn, roomDto.checkIn) &&
                Objects.equals(checkOut, roomDto.checkOut) &&
                Objects.equals(name, roomDto.name) &&
                Objects.equals(lastname, roomDto.lastname) &&
                Objects.equals(telephone, roomDto.telephone) &&
                Objects.equals(city, roomDto.city);
    }

    @Override
    public int hashCode() {

        return Objects.hash(roomNumber, bedType, checkIn, checkOut, name, lastname, telephone, city, price);
    }
}
