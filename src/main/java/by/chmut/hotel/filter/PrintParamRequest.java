package by.chmut.hotel.filter;

import javax.servlet.*;
import java.io.IOException;
import java.util.Map;

//@WebFilter(filterName = "PrintParam", urlPatterns = "/*")
public class PrintParamRequest implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Map<String, String[]> map = servletRequest.getParameterMap();
           for(String paramName:map.keySet()) {
            String[] paramValues = map.get(paramName);

            for(String valueOfParam:paramValues) {
                System.out.println(paramName+" : "+valueOfParam);
            }
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
