package by.chmut.hotel.controller.command.impl;


import by.chmut.hotel.controller.command.Command;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.chmut.hotel.controller.command.impl.constant.Constants.REMEMBER_ME_COOKIE;


public class LogoutCommand implements Command {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        req.getSession().removeAttribute("user");

        removeRememberMe(req, resp);

        String contextPath = req.getContextPath();

        resp.sendRedirect(contextPath + "/frontController?commandName=" + req.getSession().getAttribute("prevPage"));

    }

    private void removeRememberMe(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            return;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(REMEMBER_ME_COOKIE)) {
                cookie.setMaxAge(0);
                cookie.setValue(null);
                resp.addCookie(cookie);
            }
        }
    }
}
