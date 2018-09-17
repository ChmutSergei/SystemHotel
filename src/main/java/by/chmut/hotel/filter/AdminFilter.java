package by.chmut.hotel.filter;

import by.chmut.hotel.bean.User;
import by.chmut.hotel.service.ServiceFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.chmut.hotel.controller.command.impl.constant.Constants.REMEMBER_ME_COOKIE;


@WebFilter(filterName = "adminFilter", urlPatterns = "/*")

public class AdminFilter implements Filter {

    private ServiceFactory factory = ServiceFactory.getInstance();

    @Override
    public void init(FilterConfig filterConfig) {
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;

        HttpServletResponse res = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");


        if (user == null) {
            checkAuthenticationAutoLogin(req, res);
        }

        checkUserRole(user, session);


        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void checkAuthenticationAutoLogin(HttpServletRequest req, HttpServletResponse res) {

        Cookie[] cookies = req.getCookies();

        if ((cookies == null || cookies.length == 0)) {
            return;
        }
        Cookie cookie = getCookie(cookies);

        User user = null;
        if (cookie != null) {

            user = factory.getUserService().getUser(cookie);

        }
        if (user != null) {
            req.getSession().setAttribute("user", user);
        }

    }

    private Cookie getCookie(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(REMEMBER_ME_COOKIE)) {
                return cookie;
            }
        }
        return null;
    }

    private void checkUserRole(User user, HttpSession session) {
        if (user != null && user.getRole().equals("admin")) {

            session.setAttribute("admin", "admin");

        } else {

            session.setAttribute("admin", "user");
        }
    }

    @Override
    public void destroy() {

    }

}



