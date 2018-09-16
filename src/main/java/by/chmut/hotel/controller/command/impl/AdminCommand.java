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


import static by.chmut.hotel.controller.command.impl.constant.Constants.MAIN_PAGE;

public class AdminCommand implements Command {

    private ServiceFactory factory = ServiceFactory.getInstance();

    private static final Logger logger = Logger.getLogger(AdminCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        req.getSession().removeAttribute("errorAdmin");

        req.getSession().removeAttribute("client");

        try {

            req.getSession().setAttribute("client", factory.getDtoService().getRoomWithCheckInOrDepartureForThisDay(LocalDate.now()));

        } catch (ServiceException e) {

            logger.error(e);

            req.getSession().setAttribute("errorAdmin", "errorAdmin");
        }

        req.getRequestDispatcher(MAIN_PAGE).forward(req,resp);
    }
}
