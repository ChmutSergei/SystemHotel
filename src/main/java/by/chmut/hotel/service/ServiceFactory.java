package by.chmut.hotel.service;

import by.chmut.hotel.service.impl.DtoServiceImpl;
import by.chmut.hotel.service.impl.ReservationServiceImpl;
import by.chmut.hotel.service.impl.RoomServiceImpl;
import by.chmut.hotel.service.impl.UserServiceImpl;

public class ServiceFactory {

    private static final ServiceFactory INSTANCE = new ServiceFactory();

    private final UserService userService= new UserServiceImpl();
    private final RoomService roomService = new RoomServiceImpl();
    private final ReservationService reservationService = new ReservationServiceImpl();
    private final DtoService dtoService = new DtoServiceImpl();

    private ServiceFactory() {}


    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    public UserService getUserService() {
        return userService;
    }

    public RoomService getRoomService() {
        return roomService;
    }

    public ReservationService getReservationService() {
        return reservationService;
    }

    public DtoService getDtoService() {
        return dtoService;
    }
}
