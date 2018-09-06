package by.chmut.hotel.controller.command.impl;

import by.chmut.hotel.controller.command.Command;
import by.chmut.hotel.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

import static by.chmut.hotel.controller.command.constant.Constants.MAIN_PAGE;

public class AdminCommand implements Command {
    private ServiceFactory factory = ServiceFactory.getInstance();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        req.getSession().setAttribute("client", factory.getDtoService().getClientInfoOnDay(LocalDate.now()));

        req.getRequestDispatcher(MAIN_PAGE).forward(req,resp);
    }
}
