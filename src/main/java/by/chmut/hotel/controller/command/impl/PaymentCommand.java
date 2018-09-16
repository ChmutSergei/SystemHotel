package by.chmut.hotel.controller.command.impl;

import by.chmut.hotel.bean.Room;
import by.chmut.hotel.bean.User;
import by.chmut.hotel.controller.command.Command;
import by.chmut.hotel.controller.command.validation.PaymentSender;
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

    private static final Logger logger = Logger.getLogger(PaymentCommand.class);


    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        List<Room> temporaryRooms = (List<Room>) req.getSession().getAttribute("tempRooms");

        if (makePaymentWithParams(req)) {

            setPaidStatusForReservation(req, temporaryRooms);

            removeAttributes(req.getSession());

            String contextPath = req.getContextPath();

            resp.sendRedirect(contextPath + "/frontController?commandName=payment&success=ok");

        } else {

            req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);
        }
    }

    private boolean makePaymentWithParams(HttpServletRequest req) {

        if ( req.getSession().getAttribute("totalSum") != null ){

            PaymentSender paymentSender = new PaymentSender(req.getParameter("numCard"), req.getParameter("nameCard"),
                    req.getParameter("cvc2"));

            return paymentSender.payment().equals("true");
        }
        return false;
    }

    private void removeAttributes(HttpSession session) {
        session.removeAttribute("tempRooms");
        session.removeAttribute("checkIn");
        session.removeAttribute("checkOut");
        session.removeAttribute("totalSum");
    }

    private void setPaidStatusForReservation(HttpServletRequest req, List<Room> rooms) {

        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");

        for (Room room : rooms) {
            int count = 0;
            int maxTries = 3;

            while (true) {

                try {
                    if (count == maxTries) {
                        break;
                    }
                    factory.getReservationService().setPaidStatus(user.getId(), room);

                    break;

                } catch (ServiceException e) {

                    logger.error(e);

                    count++;
                }
            }
        }
    }
}
