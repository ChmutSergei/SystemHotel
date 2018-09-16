package by.chmut.hotel.controller.command.impl;

import by.chmut.hotel.controller.command.Command;
import by.chmut.hotel.service.ServiceException;
import by.chmut.hotel.service.ServiceFactory;
import org.apache.log4j.Logger;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.chmut.hotel.controller.command.impl.constant.Constants.MAIN_PAGE;
import static by.chmut.hotel.controller.command.impl.constant.Constants.PATH_FOR_ERROR_PAGE;


public class DefaultCommand implements Command {

    private ServiceFactory factory = ServiceFactory.getInstance();

    private static final Logger logger = Logger.getLogger(DefaultCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        try {
            req.getSession().setAttribute("rooms", factory.getRoomService().getAllRoom());

        } catch (ServiceException e) {

            logger.error(e);

            req.getSession().setAttribute("pagePath", PATH_FOR_ERROR_PAGE);

            req.getSession().setAttribute("message", "main.error");
        }

        req.getRequestDispatcher(MAIN_PAGE).forward(req,resp);

    }
}
