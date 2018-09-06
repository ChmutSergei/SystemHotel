package by.chmut.hotel.controller.command.impl;


import by.chmut.hotel.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SetRoomIdCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int roomId = Integer.parseInt(req.getParameter("roomId"));
        req.getSession().setAttribute("roomId",roomId);
    }
}
