package by.chmut.hotel.controller.command.impl;

import by.chmut.hotel.controller.command.Command;
import by.chmut.hotel.service.ServiceException;
import by.chmut.hotel.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
;

import static by.chmut.hotel.controller.command.impl.constant.Constants.MAIN_PAGE;

public class AdminCommand implements Command {

    private ServiceFactory factory = ServiceFactory.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        try {
            req.getSession().setAttribute("client", factory.getDtoService().getRoomWithCheckInOrDepartureForThisDay(LocalDate.now()));

        } catch (ServiceException e) {
            Logger logger = (Logger) req.getServletContext().getAttribute("log4j");
            logger.error(e.getMessage(),e);
        }

        req.getRequestDispatcher(MAIN_PAGE).forward(req,resp);
    }
}
