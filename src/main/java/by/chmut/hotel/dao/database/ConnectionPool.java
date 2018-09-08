package by.chmut.hotel.dao.database;

import by.chmut.hotel.dao.DAOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ConnectionPool {

    private static ConnectionPool INSTANCE;

    private List<Connection> connectionPool = new ArrayList<Connection>();
    private static final int MIN_CONNECTIONS = 5;
    private final int  maxConnections;
    private int countOnline = 0;
    private int countOffline = 0;

    private final String bundleName = "mysql";

    private final String URL;
    private final String DRIVER;
    private final String USER;
    private final String PASSWORD;

    {
        ResourceBundle rb = ResourceBundle.getBundle(bundleName);
        if (rb == null) {
            URL = "UNDEFINED";
            USER = "UNDEFINED";
            PASSWORD = "UNDEFINED";
            DRIVER = "com.mysql.jdbc.Driver";
        } else {
            URL = rb.getString("url");
            USER = rb.getString("user");
            PASSWORD = rb.getString("password");
            DRIVER = rb.getString("driver");
        }
    }
    private ConnectionPool(int maxConnections) throws DAOException {

        this.maxConnections = maxConnections;

        countOffline = maxConnections;

        initConnectionPool();
    }

    private void initConnectionPool() throws DAOException {

        while (!isConnectionPoolFull()) {
            connectionPool.add(simpleConnection());
        }

    }


    private boolean isConnectionPoolFull() {
        if(connectionPool.size() < maxConnections) {
            return false;
        }
        return true;
    }

    private Connection simpleConnection() throws DAOException {
        try {
            Class.forName(DRIVER);

            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            countOnline += 1;

            countOffline -= 1;

            return connection;

        } catch(SQLException e) {
            throw new DAOException("Error with get Connection", e);
        } catch (ClassNotFoundException e) {
            throw new DAOException("Error with get Connection - driver not found",e);
        }
    }

    public static synchronized ConnectionPool getInstance(int maxConnections) throws DAOException {
        if (INSTANCE == null) {
            INSTANCE = new ConnectionPool(maxConnections);
            return INSTANCE;
        } else {
            return INSTANCE;
        }
    }

    public synchronized Connection getConnection() throws DAOException {
        Connection connection = null;

        do {
            if (connectionPool.size()>0) {
                connection = connectionPool.get(0);
                connectionPool.remove(0);
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new DAOException("Error with get Connection from Pool",e);
                }
            }
        } while (connection == null);

        checkCountConnectionInPoolOnMinimum();

        return connection;
    }

    private synchronized void checkCountConnectionInPoolOnMinimum() throws DAOException {

        if (connectionPool.size()<maxConnections) {

            while ((connectionPool.size()<MIN_CONNECTIONS) && (countOffline>0)) {

                Connection connection = simpleConnection();

                connectionPool.add(connection);
            }
        }
    }

    public void returnConnection(Connection connection) throws DAOException {

        connectionPool.add(connection);

        checkAndCloseUnusedConnection();
    }

    private synchronized void checkAndCloseUnusedConnection() throws DAOException {
        if (connectionPool.size() > maxConnections) {
            while (connectionPool.size() > maxConnections) {
                try {
                    Connection connection = connectionPool.get(0);
                    connectionPool.remove(0);
                    connection.close();
                    countOffline -= 1;
                    countOnline += 1;

                } catch (SQLException e) {
                    throw new DAOException("Error with close Connection",e);
                }
            }
        }
    }



}
