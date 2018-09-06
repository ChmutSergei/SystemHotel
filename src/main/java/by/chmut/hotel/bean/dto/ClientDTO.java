package by.chmut.hotel.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@AllArgsConstructor
@Data
@NoArgsConstructor

public class ClientDTO {

    private int roomNumber;
    private int bedType;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String name;
    private String lastname;
    private String telephone;
    private String city;
    private double price;





}
