package by.chmut.hotel.controller;

import by.chmut.hotel.controller.command.CommandDirector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(urlPatterns = "/frontController")
public class Controller extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageName = req.getParameter("pageName");
        if (pageName == null || pageName.isEmpty()) {
            pageName = "home";
        }
        HttpSession session = req.getSession();
        CommandDirector command = CommandDirector.selectCommand(pageName);
        session.setAttribute("pagePath",command.getPagePath());
        req.setAttribute("title",command.getPageName());
        command.getCommand().execute(req,resp);
        session.setAttribute("prevPage",pageName);

    }

}
