package by.chmut.hotel.controller;

import by.chmut.hotel.controller.command.CommandType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.chmut.hotel.controller.command.CommandType.HOME;

public class RequestHandler {

    private static final String PARAMETER_NAME = "commandName";


    public static CommandType get(HttpServletRequest req) {

        String command = req.getParameter(PARAMETER_NAME);

        CommandType commandType = getCommand(command);

        setAttributesByPageName(req, commandType);

        return commandType;
    }


    private static CommandType getCommand(String command) {

        if (!(command == null || command.isEmpty())) {

            for (CommandType type : CommandType.values()) {

                String commandName = type.getCommandName();

                if (commandName.equalsIgnoreCase(command)) {

                    return type;
                }
            }
        }
        return HOME;
    }


    private static void setAttributesByPageName(HttpServletRequest request, CommandType commandType) {

        HttpSession session = request.getSession();

        String title = (String) session.getAttribute("title");

        if (title == null) {

            title = HOME.getCommandName();

        }
        if (!title.equals(commandType.getCommandName())) {

            session.setAttribute("prevPage", title);

        }
        session.setAttribute("title", commandType.getCommandName());

        session.setAttribute("pagePath", commandType.getPagePath());

    }

}
