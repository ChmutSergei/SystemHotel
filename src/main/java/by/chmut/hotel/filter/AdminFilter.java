package by.chmut.hotel.filter;

import by.chmut.hotel.bean.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter(filterName = "adminFilter", urlPatterns = "/frontController")

public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String contextPath = req.getContextPath();
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user != null && user.getRole().equals("admin")) {
            session.setAttribute("admin", "admin");
        } else {
            session.setAttribute("admin","user");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

}



