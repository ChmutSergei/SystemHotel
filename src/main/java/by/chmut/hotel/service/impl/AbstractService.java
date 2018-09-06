package by.chmut.hotel.service.impl;

import by.chmut.hotel.dao.database.ConnectionManagerException;

import java.sql.SQLException;

import static by.chmut.hotel.dao.database.ConnectionManager.getConnection;

public abstract class AbstractService {

    public void startTransaction() throws SQLException {
        getConnection().setAutoCommit(false);
    }

    public void commit() throws SQLException {
        getConnection().commit();
    }
    public void rollback() {
        try {
            getConnection().rollback();
        } catch (SQLException e) {
            throw new ConnectionManagerException("rollback error");
        }
    }
}
