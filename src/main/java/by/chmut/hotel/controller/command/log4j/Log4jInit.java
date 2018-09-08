package by.chmut.hotel.controller.command.log4j;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(loadOnStartup = 1, urlPatterns = "/log4j",name = "Log4j",
        initParams = {@WebInitParam(name = "logfile", value = "WEB-INF/log4j.properties")})

public class Log4jInit extends HttpServlet {

    @Override

    public void init() {

        String logfilename = getInitParameter("logfile");

        String pref = getServletContext().getRealPath("/");

        PropertyConfigurator.configure(pref + logfilename);

        Logger logger = Logger.getRootLogger();

        getServletContext().setAttribute("log4j", logger);

        getServletContext().setAttribute("logfilename", logfilename);

    }
}
