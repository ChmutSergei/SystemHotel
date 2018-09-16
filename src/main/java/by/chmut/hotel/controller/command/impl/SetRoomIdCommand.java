package by.chmut.hotel.controller.command.impl;


import by.chmut.hotel.controller.command.Command;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SetRoomIdCommand implements Command {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {

        int roomId = Integer.parseInt(req.getParameter("roomId"));

        req.getSession().setAttribute("roomId",roomId);

    }
}
