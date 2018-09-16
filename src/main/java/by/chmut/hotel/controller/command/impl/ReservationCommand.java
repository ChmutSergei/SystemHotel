package by.chmut.hotel.controller.command.impl;

import by.chmut.hotel.bean.Reservation;
import by.chmut.hotel.bean.Room;
import by.chmut.hotel.bean.User;
import by.chmut.hotel.controller.command.Command;
import by.chmut.hotel.service.ServiceException;
import by.chmut.hotel.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static by.chmut.hotel.controller.command.impl.constant.Constants.MAIN_PAGE;


public class ReservationCommand implements Command {

    private ServiceFactory factory = ServiceFactory.getInstance();

    private static final Logger logger = Logger.getLogger(ReservationCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        session.removeAttribute("error");
        long totalSum = getTotalSum(session);
        List<Room> temporaryRooms = getRooms(session);
        Integer roomId = (Integer) session.getAttribute("roomId");
        Integer temporaryNumber = (Integer) session.getAttribute("tempNum");
        User user = (User) session.getAttribute("user");

        // search - if user user has paid reservation - set to session for view
        setPaidRoomsInSession(req, user);

        try {
            // Add room
            if (roomId != null) {
                Room room = getRoomById(req, roomId);
                temporaryRooms.add(room);
                factory.getReservationService().save(new Reservation(user.getId(), room.getId(), room.getCheckIn(), room.getCheckOut()));
                totalSum += room.getPrice();
                session.removeAttribute("roomId");
                session.setAttribute("tempRooms", temporaryRooms);
                session.setAttribute("totalSum", totalSum);
            }
            // Remove room
            if (temporaryNumber != null) {
                Room room = getRoomByIdFromTemp(temporaryRooms, temporaryNumber);
                temporaryRooms.remove(room);
                factory.getReservationService().deleteTemporaryReservation(user.getId(), room);
                totalSum -= room.getPrice();
                session.removeAttribute("tempNum");
                session.setAttribute("tempRooms", temporaryRooms);
                session.setAttribute("totalSum", totalSum);
            }
        } catch (ServiceException e) {
            logger.error(e);
            req.getSession().setAttribute("error", "errorReservation");
        }


        // set Empty message
        if (temporaryRooms.isEmpty()) {
            req.setAttribute("emptyMsg", "reserv.emptyList");
        }
        req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);


    }

    private void setPaidRoomsInSession(HttpServletRequest req, User user) {
        try {
            req.getSession().setAttribute("paidRooms", factory.getReservationService().getPaidRoomsIfUserHasThem(user));
        } catch (ServiceException e) {
            logger.error(e);
        }
    }


    private Room getRoomById(HttpServletRequest req, int roomId) throws ServiceException {
        Room result;
        result = factory.getRoomService().get(roomId);
        result.setTemporaryNumber((int) (Math.random() * 1000000) + (int) (Math.random() * 1000));
        result.setCheckIn((LocalDate) req.getSession().getAttribute("checkIn"));
        result.setCheckOut((LocalDate) req.getSession().getAttribute("checkOut"));
        return result;
    }

    private Room getRoomByIdFromTemp(List<Room> temporaryRooms, int temporaryNumber) {
        Room result = new Room();
        for (Room room : temporaryRooms) {
            if (room.getTemporaryNumber() == temporaryNumber) {
                result = room;
            }
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

    private List<Room> getRooms(HttpSession session) {

        List<Room> result = (List<Room>) session.getAttribute("tempRooms");

        if (result != null) {
            return result;
        }

        result = new ArrayList<>();

        session.setAttribute("tempRooms", new ArrayList<Room>());

        return result;
    }

}

