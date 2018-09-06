package by.chmut.hotel.controller.command.impl;

import by.chmut.hotel.controller.command.Command;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.chmut.hotel.controller.command.constant.Constants.MAIN_PAGE;


public class AddAccountCommand implements Command {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        if (req.getSession().getAttribute("user") == null) {
            req.getRequestDispatcher(MAIN_PAGE).forward(req, resp);
            req.getSession().setAttribute("errorMsg", "");
        } else {
            String contextPath = req.getContextPath();
            resp.sendRedirect(contextPath + "/frontController?pageName=reservation");
        }
    }
}
