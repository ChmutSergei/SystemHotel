package by.chmut.hotel.filter;



import by.chmut.hotel.controller.command.CommandDirector;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.chmut.hotel.controller.command.CommandDirector.PAYMENT;
import static by.chmut.hotel.controller.command.CommandDirector.RESERVATION;

@WebFilter (urlPatterns = "/frontController")
public class AuthenticationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig){

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        CommandDirector type = CommandDirector.selectCommand(req.getParameter("pageName"));
        if (RESERVATION.equals(type)||PAYMENT.equals(type)) {
            String contextPath = req.getContextPath();
            HttpSession session = req.getSession();
            if((session.getAttribute("user") == null)) {
                res.sendRedirect(contextPath + "/frontController?pageName=add_account");

                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
