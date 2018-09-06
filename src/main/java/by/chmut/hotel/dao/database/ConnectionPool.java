package by.chmut.hotel.dao.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ConnectionPool {

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
            System.out.println("Бандл для db не был инициализирован");
        } else {
            URL = rb.getString("url");
            USER = rb.getString("user");
            PASSWORD = rb.getString("password");
            DRIVER = rb.getString("driver");
        }
    }
    public ConnectionPool(int maxConnections) {
//        this.bundleName = bundleName;
        this.maxConnections = maxConnections;
        countOffline = maxConnections;
        initConnectionPool();
    }

    private void initConnectionPool() {
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

    private Connection simpleConnection() {
        try {
            Class.forName(DRIVER);
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            countOnline += 1;
            countOffline -= 1;
            return connection;
        } catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public synchronized Connection getConnection() {
        Connection connection = null;

        do {
            if (connectionPool.size()>0) {
                connection = connectionPool.get(0);
                connectionPool.remove(0);
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } while (connection == null);

        checkCountConnectionInPoolOnMinimum();

        return connection;
    }

    private synchronized void checkCountConnectionInPoolOnMinimum() {
        if (connectionPool.size()<maxConnections) {
            while ((connectionPool.size()<MIN_CONNECTIONS) && (countOffline>0)) {
                Connection connection = simpleConnection();
                connectionPool.add(connection);
            }
        }
    }

    public void returnConnection(Connection connection) {
        connectionPool.add(connection);
        checkAndCloseUnusedConnection();
    }

    private synchronized void checkAndCloseUnusedConnection() {
        if (connectionPool.size() > maxConnections) {
            while (connectionPool.size() > maxConnections) {
                try {
                    Connection connection = connectionPool.get(0);
                    connectionPool.remove(0);
                    connection.close();
                    countOffline -= 1;
                    countOnline += 1;

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
