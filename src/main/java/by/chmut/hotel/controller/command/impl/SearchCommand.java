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
import static by.chmut.hotel.controller.command.impl.constant.Constants.PATH_FOR_ERROR_PAGE;


public class SearchCommand implements Command {

    private ServiceFactory factory = ServiceFactory.getInstance();

    private static final Logger logger = Logger.getLogger(SearchCommand.class);


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String bedType = req.getParameter("bedtype");
        String checkIn = req.getParameter("checkin");
        String checkOut = req.getParameter("checkout");
        List<Room> rooms = new ArrayList<>();
        try {
            if (bedType == null || checkIn == null || checkOut == null) {

                rooms = factory.getRoomService().getAllRoom();

            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                LocalDate checkInDate = LocalDate.parse(checkIn, formatter);
                LocalDate checkOutDate = LocalDate.parse(checkOut, formatter);
                int bedTypeInt = Integer.parseInt(bedType);
                req.getSession().setAttribute("checkIn", checkInDate);
                req.getSession().setAttribute("checkOut", checkOutDate);

                rooms = factory.getRoomService().getAvailableRoom(bedTypeInt,
                        checkInDate, checkOutDate);
            }
        } catch (ServiceException e) {

            logger.error(e);

            req.getSession().setAttribute("pagePath", PATH_FOR_ERROR_PAGE);

            req.getSession().setAttribute("message", "main.error");

        }

        req.getSession().setAttribute("rooms", rooms);

        req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);

    }
}
