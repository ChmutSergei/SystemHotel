package by.chmut.hotel.controller.command;

import by.chmut.hotel.controller.command.impl.*;

public enum CommandType {

    HOME("Home", "pages/main.jspx", new DefaultCommand()),

    LOGIN("Login", "",  new LoginCommand()),

    LOGOUT("Logout", "", new LogoutCommand()),

    SEARCH("Search", "pages/search.jspx", new SearchCommand()),

    ADD_ACCOUNT("Add_account", "pages/autorization.jspx", new AddAccountCommand()),

    CREATE_USER("Create_user","", new CreateUserCommand()),

    RESERVATION("Reservation", "pages/reservation.jspx", new ReservationCommand()),

    PAYMENT("Payment", "pages/payment.jspx", new PaymentCommand()),

    ADMIN("Administration", "pages/administration.jspx", new AdminCommand()),

    SET_ROOM_ID("SetRoomId", "", new SetRoomIdCommand()),

    SET_UNIQUE_NUMBER("SetUniqueNumber", "", new SetUniqueNumRoomCommand());

    private String commandName;
    private String pagePath;
    private Command command;

    CommandType(String commandName, String pagePath, Command command) {
        this.commandName = commandName;
        this.pagePath = pagePath;
        this.command = command;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getPagePath() {
        return pagePath;
    }

    public Command getCommand() {
        return command;
    }
}
