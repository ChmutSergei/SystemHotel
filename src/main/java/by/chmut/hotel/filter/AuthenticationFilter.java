package by.chmut.hotel.filter;

import by.chmut.hotel.bean.User;
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
import static by.chmut.hotel.controller.command.CommandType.ADMIN;

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

        String contextPath = req.getContextPath();

        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("user");


        if (RESERVATION.equals(type)||PAYMENT.equals(type)||ADMIN.equals(type)) {

            if (user == null)  {

                session.setAttribute("errorMsg", "accessLog");

                res.sendRedirect(contextPath + "/frontController?commandName=add_account");

                return;
            }
            if (!user.getRole().equals("admin") & ADMIN.equals(type)) {

                res.sendRedirect(contextPath + "/frontController?commandName=home");

                return;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
