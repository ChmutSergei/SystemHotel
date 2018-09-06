package by.chmut.hotel.controller.command;

import by.chmut.hotel.controller.command.impl.*;

public enum CommandDirector {
    HOME("Home", "pages/main.jspx", new DefaultCommand()),
    LOGIN("Login", "",  new LoginCommand()),
    LOGOUT("Logout", "", new LogoutCommand()),
    SEARCH("Search", "pages/search.jspx", new SearchCommand()),
    ADD_ACCOUNT("Add_account", "pages/autorization.jspx", new AddAccountCommand()),
    CREATE_USER("Create_user","", new CreateUserCommand()),
    RESERVATION("Reservation", "pages/reservation.jspx", new ReservationCommand()),
    PAYMENT("Payment", "pages/payment.jspx", new PaymentCommand()),
    ADMIN("Administration", "pages/administration.jspx", new AdminCommand()),
    SETROOMID("SetRoomId", "", new SetRoomIdCommand());

    private String pageName;
    private String pagePath;
    private Command command;

    CommandDirector(String pageName, String pagePath, Command command) {
        this.pageName = pageName;
        this.pagePath = pagePath;
        this.command = command;
    }

    public static CommandDirector selectCommand(String pageName) {
        for (CommandDirector type : CommandDirector.values()) {
            if (type.pageName.equalsIgnoreCase(pageName)) {
                return type;
            }
        }
        return HOME;
    }

    public String getPageName() {
        return pageName;
    }


    public String getPagePath() {
        return pagePath;
    }

    public Command getCommand() {
        return command;
    }
}
