package by.chmut.hotel.controller.command.impl;

import by.chmut.hotel.bean.Reservation;
import by.chmut.hotel.bean.Room;
import by.chmut.hotel.bean.User;
import by.chmut.hotel.controller.command.Command;
import by.chmut.hotel.service.ReservationService;
import by.chmut.hotel.service.RoomService;
import by.chmut.hotel.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static by.chmut.hotel.controller.command.constant.Constants.MAIN_PAGE;


public class ReservationCommand implements Command {
    private ServiceFactory factory = ServiceFactory.getInstance();
    private RoomService roomService = factory.getRoomService();
    private ReservationService reservationService = factory.getReservationService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        double totalSum;
        User user = (User) session.getAttribute("user");
        // search - if user user has paid reservation - set to session for view
        if (user != null) {
            List<Reservation> lastReservations = reservationService.getByUserId(user.getId());
            if (!lastReservations.isEmpty()) {
                List<Room> paidRooms = new ArrayList<>();
                for (Reservation reservation : lastReservations) {
                    Room room = roomService.get(reservation.getRoomId());
                    room.setCheckIn(reservation.getCheckIn());
                    room.setCheckOut(reservation.getCheckOut());
                    paidRooms.add(room);
                }
                session.setAttribute("paidRooms", paidRooms);
            }
        }
        if (session.getAttribute("totalSum") != null) {
            totalSum = (Double) session.getAttribute("totalSum");
        } else {
            totalSum = 0;
            session.setAttribute("totalSum", totalSum);
        }
        // Add room
        List<Room> roomTemp = new ArrayList<>();
        if (session.getAttribute("roomTemp") != null) {
            roomTemp = (List<Room>) session.getAttribute("roomTemp");
        }
        if ((req.getParameter("delete") == null)&&(session.getAttribute("roomId") != null)) {
            int roomId = (Integer) session.getAttribute("roomId");
            session.removeAttribute("roomId");
            Room room = roomService.get(roomId);
            room.setCheckIn((LocalDate) req.getSession().getAttribute("checkIn"));
            room.setCheckOut((LocalDate) req.getSession().getAttribute("checkOut"));
            roomTemp.add(room);
            totalSum = (Double) session.getAttribute("totalSum") + room.getPrice();
            totalSum = roundResult(totalSum);
            session.setAttribute("totalSum", totalSum);
            session.setAttribute("roomTemp", roomTemp);
        }
        // Remove room
        if ((req.getParameter("delete") != null)&&(session.getAttribute("roomId")!=null)) {
            int delIndex = -1;
            for (int i = 0; i < roomTemp.size(); i++) {
                Room room = roomTemp.get(i);
                if (room.getId() == (int) (session.getAttribute("roomId"))) {
                    delIndex = i;
                    totalSum = (Double) session.getAttribute("totalSum") - room.getPrice();
                    totalSum = roundResult(totalSum);
                }
            }
            if (delIndex != -1) {
                roomTemp.remove(delIndex);
                session.setAttribute("roomTemp", roomTemp);
                session.removeAttribute("roomId");
                session.setAttribute("totalSum", totalSum);
            }
        }
        if (roomTemp.isEmpty()) {
            req.setAttribute("emptyMsg", "reserv.emptyList");
        }
        req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);
    }


    private double roundResult (double d) {
        d = d*100;
        int i = (int) Math.round(d);
        return (double) i/100;
    }

}
