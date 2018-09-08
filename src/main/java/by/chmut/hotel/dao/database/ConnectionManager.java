package by.chmut.hotel.dao.database;

import by.chmut.hotel.dao.DAOException;

import java.sql.Connection;


public class ConnectionManager {

    private static final int MAX_CONNECTIONS = 20;

    private static ThreadLocal<Connection> tl = new ThreadLocal<>();

    public static Connection getConnection() throws DAOException {

            if (tl.get() == null) {

                tl.set(ConnectionPool.getInstance(MAX_CONNECTIONS).getConnection());

            }

            return tl.get();
    }
}