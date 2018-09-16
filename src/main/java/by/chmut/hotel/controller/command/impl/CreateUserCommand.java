package by.chmut.hotel.controller.command.impl;

import by.chmut.hotel.bean.User;
import by.chmut.hotel.controller.command.Command;
import by.chmut.hotel.controller.command.validation.encoder.Encoder;
import by.chmut.hotel.service.ServiceException;
import by.chmut.hotel.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateUserCommand implements Command {

    private ServiceFactory factory = ServiceFactory.getInstance();

    private static final Logger logger = Logger.getLogger(CreateUserCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String contextPath = req.getContextPath();

        String message = "haveuser";

        String url = contextPath + "/frontController?commandName=add_account";

        try {
            User user = factory.getUserService().addUser(req.getParameter("login"), Encoder.encode(req.getParameter("password")),
                    req.getParameter("firstName"), req.getParameter("lastName"),
                    req.getParameter("email"), req.getParameter("phone"), req.getParameter("country"), req.getParameter("city"),
                    req.getParameter("address"), req.getParameter("zip"));

            if (user != null) {

                req.getSession().setAttribute("user", user);

                message = "";

                url = contextPath + "/frontController?commandName=reservation";
            }

        } catch (ServiceException e) {

            logger.error(e);

            message = "addUser.error";
        }

        req.getSession().setAttribute("errorMsg", message);

        resp.sendRedirect(url);
    }
}
