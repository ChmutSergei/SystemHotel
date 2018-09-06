package by.chmut.hotel.controller.command.impl;

import by.chmut.hotel.bean.Room;
import by.chmut.hotel.controller.command.Command;
import by.chmut.hotel.service.RoomService;
import by.chmut.hotel.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static by.chmut.hotel.controller.command.constant.Constants.MAIN_PAGE;


public class SearchCommand implements Command {
    private ServiceFactory factory = ServiceFactory.getInstance();
    private RoomService roomService = factory.getRoomService();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String bedType = req.getParameter("bedtype");
        String checkIn = req.getParameter("checkin");
        String checkOut = req.getParameter("checkout");
        LocalDate checkInDate;
        LocalDate checkOutDate;
        int bedTypeInt;
        List<Room> rooms = new ArrayList<>();
        if (bedType == null||checkIn==null||checkOut==null) {
            rooms = roomService.getAllRoom();
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            checkInDate = LocalDate.parse(checkIn,formatter);
            checkOutDate = LocalDate.parse(checkOut,formatter);
            bedTypeInt = Integer.parseInt(bedType);
            req.getSession().setAttribute("checkIn",checkInDate);
            req.getSession().setAttribute("checkOut",checkOutDate);
            rooms = roomService.getRoomOnDateAndBedType(bedTypeInt,
                    checkInDate, checkOutDate);
        }
        req.getSession().setAttribute("rooms", rooms);
        req.getRequestDispatcher(MAIN_PAGE).forward(req,resp);


    }
}
