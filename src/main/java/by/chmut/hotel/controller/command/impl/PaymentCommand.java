package by.chmut.hotel.controller.command.impl;

import by.chmut.hotel.bean.Reservation;
import by.chmut.hotel.bean.Room;
import by.chmut.hotel.bean.User;
import by.chmut.hotel.controller.command.Command;
import by.chmut.hotel.service.ReservationService;
import by.chmut.hotel.service.ServiceException;
import by.chmut.hotel.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static by.chmut.hotel.controller.command.impl.constant.Constants.MAIN_PAGE;

public class PaymentCommand implements Command {

    private ServiceFactory factory = ServiceFactory.getInstance();

    private ReservationService reservationService = factory.getReservationService();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        List<Room> rooms = (List<Room>) req.getSession().getAttribute("roomTemp");

        // Create reservation
        if (("1234567890".equals(req.getParameter("payment")))) {

            createReservation(req, rooms);

            removeAttributesAndSetSuccess(req.getSession());
        }

        String numCard = req.getParameter("numCard");

        String nameCard = req.getParameter("nameCard");

        String cvc2 = req.getParameter("cvc2");

        if ((numCard!=null)&&(nameCard!=null)&&(cvc2!=null)) {

            String contextPath = req.getContextPath();

            resp.sendRedirect(contextPath+ "/frontController?pageName=payment&payment=1234567890");

        } else {

            req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);
        }
    }

    private void removeAttributesAndSetSuccess(HttpSession session) {
        // Remove attribute
        session.removeAttribute("roomTemp");
        session.removeAttribute("checkIn");
        session.removeAttribute("checkOut");
        session.removeAttribute("totalSum");
        // Set success )))
        session.setAttribute("success","yes!");
    }

    private void createReservation(HttpServletRequest req, List<Room> rooms) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        int userId = user.getId();
        for (Room room:rooms) {
            Reservation reservation = new Reservation();
            reservation.setUserId(userId);
            reservation.setRoomId(room.getId());
            reservation.setCheckIn((LocalDate) session.getAttribute("checkIn"));
            reservation.setCheckOut((LocalDate) session.getAttribute("checkOut"));
            reservation.setDate(LocalDate.now());
            try {
                reservationService.save(reservation);
            } catch (ServiceException e) {
                Logger logger = (Logger) req.getServletContext().getAttribute("log4j");
                logger.error(e.getMessage(),e);
            }
        }
    }
}
