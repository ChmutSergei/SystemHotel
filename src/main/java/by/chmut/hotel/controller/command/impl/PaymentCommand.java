package by.chmut.hotel.controller.command.impl;

import by.chmut.hotel.bean.Reservation;
import by.chmut.hotel.bean.Room;
import by.chmut.hotel.bean.User;
import by.chmut.hotel.controller.command.Command;
import by.chmut.hotel.controller.command.validation.PaymentSender;
import by.chmut.hotel.service.ReservationService;
import by.chmut.hotel.service.ServiceException;
import by.chmut.hotel.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static by.chmut.hotel.controller.command.impl.constant.Constants.MAIN_PAGE;

public class PaymentCommand implements Command {

    private ServiceFactory factory = ServiceFactory.getInstance();

    private ReservationService reservationService = factory.getReservationService();


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        List<Room> temporaryRooms = (List<Room>) req.getSession().getAttribute("tempRooms");

        // Create reservation
        if ("success".equals(req.getParameter("payment"))) {

            setPaidStatusForReservation(req, temporaryRooms);

            removeAttributesAndSetSuccess(req.getSession());
        }

        // check payment
        PaymentSender paymentSender = new PaymentSender(req.getParameter("numCard"), req.getParameter("nameCard"),
                req.getParameter("cvc2"));

        String request = paymentSender.payment();

        if (request.equals("true")) {

            String contextPath = req.getContextPath();

            resp.sendRedirect(contextPath+ "/frontController?commandName=payment&payment=success");

        } else {

            req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);
        }
    }

    private void removeAttributesAndSetSuccess(HttpSession session) {
        // Remove attribute
        session.removeAttribute("tempRooms");
        session.removeAttribute("checkIn");
        session.removeAttribute("checkOut");
        session.removeAttribute("totalSum");
        // Set success )))
        session.setAttribute("success","yes!");
    }

    private void setPaidStatusForReservation(HttpServletRequest req, List<Room> rooms) {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        for (Room room:rooms) {
            try {
                reservationService.setPaidStatus(user.getId(),room);
            } catch (ServiceException e) {
                Logger logger = (Logger) req.getServletContext().getAttribute("log4j");
                logger.error(e.getMessage(),e);
            }
        }
    }
}
