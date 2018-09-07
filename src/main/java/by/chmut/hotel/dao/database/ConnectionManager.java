package by.chmut.hotel.dao.database;

import by.chmut.hotel.dao.DAOException;

import java.sql.Connection;


public class ConnectionManager {

    private static ConnectionPool INSTANCE;

    static {

        try {

            INSTANCE = new ConnectionPool( 20);

        } catch (DAOException e) {

            new DAOException("Error with Connection Pool - ",e);

        }
    }

    private static ThreadLocal<Connection> tl = new ThreadLocal<>();

    public static Connection getConnection() throws DAOException {

            if (tl.get() == null) {

                tl.set(INSTANCE.getConnection());

            }

            return tl.get();
    }
}