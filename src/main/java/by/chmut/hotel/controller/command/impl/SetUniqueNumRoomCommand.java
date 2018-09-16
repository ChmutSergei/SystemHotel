package by.chmut.hotel.controller.command.impl;

import by.chmut.hotel.controller.command.Command;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SetUniqueNumRoomCommand implements Command {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) {

        int tempNumber = Integer.parseInt(req.getParameter("tempNumber"));

        req.getSession().setAttribute("tempNum",tempNumber);

    }
}
