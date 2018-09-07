package by.chmut.hotel.controller.command.impl;

import by.chmut.hotel.bean.Room;
import by.chmut.hotel.controller.command.Command;
import by.chmut.hotel.service.RoomService;
import by.chmut.hotel.service.ServiceException;
import by.chmut.hotel.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static by.chmut.hotel.controller.command.impl.constant.Constants.MAIN_PAGE;


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
            try {
                rooms = roomService.getAllRoom();
            } catch (ServiceException e) {
                Logger logger = (Logger) req.getServletContext().getAttribute("log4j");
                logger.error(e.getMessage(),e);
            }
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            checkInDate = LocalDate.parse(checkIn,formatter);
            checkOutDate = LocalDate.parse(checkOut,formatter);
            bedTypeInt = Integer.parseInt(bedType);
            req.getSession().setAttribute("checkIn",checkInDate);
            req.getSession().setAttribute("checkOut",checkOutDate);
            try {
                rooms = roomService.getRoomByDateAndBedType(bedTypeInt,
                        checkInDate, checkOutDate);
            } catch (ServiceException e) {
                Logger logger = (Logger) req.getServletContext().getAttribute("log4j");
                logger.error(e.getMessage(),e);
            }
        }
        req.getSession().setAttribute("rooms", rooms);
        req.getRequestDispatcher(MAIN_PAGE).forward(req,resp);


    }
}
