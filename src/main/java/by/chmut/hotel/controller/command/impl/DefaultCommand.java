package by.chmut.hotel.controller.command.impl;

import by.chmut.hotel.controller.command.Command;
import by.chmut.hotel.service.RoomService;
import by.chmut.hotel.service.ServiceFactory;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.chmut.hotel.controller.command.constant.Constants.MAIN_PAGE;


public class DefaultCommand implements Command {
    private ServiceFactory factory = ServiceFactory.getInstance();
    private RoomService roomService =factory.getRoomService();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getSession().setAttribute("rooms", roomService.getAllRoom());

        req.getRequestDispatcher(MAIN_PAGE).forward(req,resp);

    }
}
