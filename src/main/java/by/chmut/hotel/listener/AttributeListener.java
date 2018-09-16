package by.chmut.hotel.listener;

import by.chmut.hotel.bean.Room;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

@WebListener
public class AttributeListener  implements HttpSessionAttributeListener {
    private static final long START_TIME_FOR_WARNING = 1800000; // 30 min - warning - pay for order
    private static final long START_TIME_FOR_REMOVE_ROOMS = 2100000; // 5 min - cancel order unlock room
    private static final long START_TIME_FOR_REMOVE_WARNING = 2100000; // remove warning message
    private static final long PERIOD = 1800000;
    private String attributeTemporaryRoom = "tempRooms";
    private String attributeError = "error";

    public void attributeAdded(HttpSessionBindingEvent ev) {

        HttpSession session = ev.getSession();

        String currentAttributeName = ev.getName();

        TimerTask warning = getWarningTask(session);

        TimerTask removeRooms = getRemoveTask(session);

        TimerTask removeWarning = getRemoveWarningTask(session);

        Timer timer = new Timer();

        if (currentAttributeName.equals(attributeTemporaryRoom)) {

            timer.schedule(warning,START_TIME_FOR_WARNING,PERIOD);

            timer.schedule(removeRooms,START_TIME_FOR_REMOVE_ROOMS,PERIOD);

            timer.schedule(removeWarning,START_TIME_FOR_REMOVE_WARNING,PERIOD);

        }

        if (currentAttributeName.equals(attributeError)) {
            removeAttributes(session);
        }
    }

    private TimerTask getRemoveTask(HttpSession session) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("UPs....");
                session.setAttribute("tempRooms",new ArrayList<Room>());
            }
        };
        return timerTask;
    }

    private TimerTask getWarningTask(HttpSession session) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Attention you have 5 min to pay for you order");
                session.setAttribute("warning","Attention");
            }
        };
        return timerTask;
    }

    private TimerTask getRemoveWarningTask(HttpSession session) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                session.removeAttribute("warning");
            }
        };
        return timerTask;
    }

    private void removeAttributes(HttpSession session) {
        session.removeAttribute("tempRooms");
        session.removeAttribute("checkIn");
        session.removeAttribute("checkOut");
        session.removeAttribute("totalSum");
        session.removeAttribute("errorMsg");
        session.removeAttribute("tempNum");
        session.removeAttribute("roomId");

    }
    @Override
    public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {

        HttpSession session = httpSessionBindingEvent.getSession();

        String currentAttributeName = httpSessionBindingEvent.getName();

        if (currentAttributeName.equals("user")) {
            removeAttributes(session);
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {

    }

}