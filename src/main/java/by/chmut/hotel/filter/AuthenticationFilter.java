package by.chmut.hotel.filter;

import by.chmut.hotel.controller.RequestHandler;
import by.chmut.hotel.controller.command.CommandType;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.chmut.hotel.controller.command.CommandType.PAYMENT;
import static by.chmut.hotel.controller.command.CommandType.RESERVATION;

@WebFilter (urlPatterns = "/frontController")
public class AuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig){
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;

        HttpServletResponse res = (HttpServletResponse) servletResponse;

        CommandType type = RequestHandler.get(req);

        if (RESERVATION.equals(type)||PAYMENT.equals(type)) {

            String contextPath = req.getContextPath();

            HttpSession session = req.getSession();

            if((session.getAttribute("user") == null)) {

                session.setAttribute("errorMsg", "accessLog");

                res.sendRedirect(contextPath + "/frontController?commandName=add_account");

                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
