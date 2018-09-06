package by.chmut.hotel.controller.command.impl;

import by.chmut.hotel.bean.User;
import by.chmut.hotel.controller.command.Command;
import by.chmut.hotel.controller.command.encoder.Encoder;
import by.chmut.hotel.service.ServiceFactory;
import by.chmut.hotel.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.chmut.hotel.controller.command.constant.Constants.MAIN_PAGE;


public class LoginCommand implements Command {
    private ServiceFactory factory = ServiceFactory.getInstance();
    private UserService userService =factory.getUserService();
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String login = req.getParameter("uname");
        String password = req.getParameter("psw");
        if (login==null || password==null) {
            RequestDispatcher dispatcher = req.getRequestDispatcher(MAIN_PAGE);
            dispatcher.forward(req, resp);
            return;
        }
        User user = userService.getUserByLogin(login);
        String contextPath = req.getContextPath();
        if (user != null && user.getPassword().equals(Encoder.encode(password))) {
            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("errorMsg", "");
            resp.sendRedirect(contextPath+ "/frontController?pageName="+req.getSession().getAttribute("prevPage"));
        } else {
            req.getSession().setAttribute("errorMsg", "errorLog");
            resp.sendRedirect(contextPath + "/frontController?pageName=add_account");
        }
    }
}
