package by.chmut.hotel.controller.command.impl;

import by.chmut.hotel.bean.User;
import by.chmut.hotel.controller.command.Command;
import by.chmut.hotel.controller.command.encoder.Encoder;
import by.chmut.hotel.service.ServiceException;
import by.chmut.hotel.service.ServiceFactory;
import by.chmut.hotel.service.UserService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateUserCommand implements Command {
    private ServiceFactory factory = ServiceFactory.getInstance();
    private UserService userService = factory.getUserService();
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        User user = null;
        try {
            user = userService.addUser(req.getParameter("login"),Encoder.encode(req.getParameter("password")),
                    req.getParameter("firstName"),req.getParameter("lastName"),
                    req.getParameter("email"), req.getParameter("phone"), req.getParameter("country"),req.getParameter("city"),
                    req.getParameter("address"), req.getParameter("zip"));

        } catch (ServiceException e) {
            Logger logger = (Logger) req.getServletContext().getAttribute("log4j");
            logger.error(e.getMessage(),e);
        }
        String contextPath = req.getContextPath();
        if (user == null) {
            req.getSession().setAttribute("errorMsg","haveuser");
            resp.sendRedirect(contextPath + "/frontController?commandName=add_account");
        } else {
            req.getSession().setAttribute("user", user);
            resp.sendRedirect(contextPath + "/frontController?commandName=reservation");
        }
    }
}
