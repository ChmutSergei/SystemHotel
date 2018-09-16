package by.chmut.hotel.controller.command.impl;


import by.chmut.hotel.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutCommand implements Command {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        req.getSession().removeAttribute("user");

        String contextPath = req.getContextPath();

        resp.sendRedirect(contextPath+ "/frontController?commandName="+req.getSession().getAttribute("prevPage"));

    }
}
