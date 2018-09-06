package by.chmut.hotel.dao.database;

import java.sql.Connection;


public class ConnectionManager {

    private static final ConnectionPool INSTANCE = new ConnectionPool( 20);

    private static ThreadLocal<Connection> tl = new ThreadLocal<>();

    public static Connection getConnection() throws ConnectionManagerException {
        try {
            if (tl.get() == null) {
                tl.set(INSTANCE.getConnection());
            }
            return tl.get();
        } catch (Exception e) {
            throw new ConnectionManagerException("Ошибка получения соединения " +  e.getMessage());
        }
    }
}