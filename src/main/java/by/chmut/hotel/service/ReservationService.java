package by.chmut.hotel.service;

import by.chmut.hotel.bean.Reservation;
import by.chmut.hotel.bean.dto.ClientDTO;

import java.io.Serializable;
import java.util.List;

public interface ReservationService {

    Reservation save(Reservation reservation);

    Reservation get(Serializable id);

    void update(Reservation reservation);

    int delete(Serializable id);

    List<Reservation> getByUserId(Serializable userId);

}
