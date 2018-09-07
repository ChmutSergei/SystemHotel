package by.chmut.hotel.controller.command.impl;

import by.chmut.hotel.bean.Reservation;
import by.chmut.hotel.bean.Room;
import by.chmut.hotel.bean.User;
import by.chmut.hotel.controller.command.Command;
import by.chmut.hotel.service.ReservationService;
import by.chmut.hotel.service.RoomService;
import by.chmut.hotel.service.ServiceException;
import by.chmut.hotel.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static by.chmut.hotel.controller.command.impl.constant.Constants.MAIN_PAGE;


public class ReservationCommand implements Command {
    private ServiceFactory factory = ServiceFactory.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        long totalSum = getTotalSum(session);
        List<Room> roomTemp = getRoomTemp(session);
        Integer roomId = (Integer) session.getAttribute("roomId");
        User user = (User) session.getAttribute("user");

        // search - if user user has paid reservation - set to session for view
        setPaidRoomsInSession(req, user);

        if (roomId != null) {
            Room room = getRoomById(req, roomId);

            if (req.getParameter("delete") == null) {
                // Add room
                roomTemp.add(room);
                totalSum += room.getPrice();
            } else {
                // Remove room
                roomTemp.remove(room);
                totalSum -= room.getPrice();
            }
            session.removeAttribute("roomId");
            session.setAttribute("roomTemp", roomTemp);
            session.setAttribute("totalSum", totalSum);
        }
        // set Empty message
        if (roomTemp.isEmpty()) {
            req.setAttribute("emptyMsg", "reserv.emptyList");
        }
        req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);

    }

    private void setPaidRoomsInSession(HttpServletRequest req,User user) {
        try {
            req.getSession().setAttribute("paidRooms", factory.getReservationService().getPaidRoomsIfUserHasThem(user));
        } catch (ServiceException e) {
            Logger logger = (Logger) req.getServletContext().getAttribute("log4j");
            logger.error(e.getMessage(),e);
        }
    }


    private Room getRoomById(HttpServletRequest req, Integer roomId) {
        Room result = new Room();
        try{
            result = factory.getRoomService().get(roomId);
            result.setCheckIn((LocalDate) req.getSession().getAttribute("checkIn"));
            result.setCheckOut((LocalDate)  req.getSession().getAttribute("checkOut"));
        } catch (ServiceException e) {
            Logger logger = (Logger) req.getServletContext().getAttribute("log4j");
            logger.error(e.getMessage(),e);
        }
        return result;
    }

    private long getTotalSum(HttpSession session) {

        if (session.getAttribute("totalSum") != null) {

            return (long) session.getAttribute("totalSum");
        }
        session.setAttribute("totalSum", 0L);

        return 0L;
    }

    private List<Room> getRoomTemp(HttpSession session) {

        List<Room> result = (List<Room>) session.getAttribute("roomTemp");

        if (result != null) {
            return result;
        }

        result = new ArrayList<>();

        session.setAttribute("roomTemp", new ArrayList<Room>());

        return result;
    }

}

