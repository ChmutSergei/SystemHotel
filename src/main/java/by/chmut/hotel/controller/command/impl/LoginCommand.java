package by.chmut.hotel.controller.command.impl;

import by.chmut.hotel.bean.User;
import by.chmut.hotel.controller.command.Command;
import by.chmut.hotel.service.ServiceException;
import by.chmut.hotel.service.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.chmut.hotel.controller.command.impl.constant.Constants.COOKIE_AGE;
import static by.chmut.hotel.controller.command.impl.constant.Constants.MAIN_PAGE;
import static by.chmut.hotel.controller.command.validation.Validator.isPasswordValid;


public class LoginCommand implements Command {

    private ServiceFactory factory = ServiceFactory.getInstance();

    private static final Logger logger = Logger.getLogger(LoginCommand.class);

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String login = req.getParameter("uname");

        String password = req.getParameter("psw");

        String remember = req.getParameter("remember");

        if (login==null || password==null) {

            RequestDispatcher dispatcher = req.getRequestDispatcher(MAIN_PAGE);

            dispatcher.forward(req, resp);

            return;
        }

        User user;

        String contextPath = req.getContextPath();

        HttpSession session = req.getSession();

        String errorMessage = "errorLog";

        String url = contextPath + "/frontController?commandName=add_account";
        try {
            user = factory.getUserService().getUser(login);

            if (isPasswordValid(user, password)) {

                session.setAttribute("user", user);

                errorMessage = "";

                url = contextPath+ "/frontController?commandName="+req.getSession().getAttribute("prevPage");

                if (remember != null) {

                    activateRememberMe(user, resp);

                }

            }
        } catch (ServiceException e) {
            logger.error(e);
        }

        session.setAttribute("errorMsg", errorMessage);

        resp.sendRedirect(url);

    }

    private void activateRememberMe(User user, HttpServletResponse resp) {

        Cookie cookie = factory.getUserService().addRememberMe(user);

        cookie.setMaxAge(COOKIE_AGE);

        resp.addCookie(cookie);

    }
}
