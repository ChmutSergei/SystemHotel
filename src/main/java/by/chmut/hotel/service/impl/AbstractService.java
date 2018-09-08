package by.chmut.hotel.service.impl;

import by.chmut.hotel.dao.DAOException;
import by.chmut.hotel.service.ServiceException;

import java.sql.SQLException;

import static by.chmut.hotel.dao.database.ConnectionManager.getConnection;

public abstract class AbstractService {

    public void startTransaction() throws ServiceException {
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            throw new ServiceException("Error with start transaction", e);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public void commit() throws ServiceException {
        try {
            getConnection().commit();
        } catch (SQLException e) {
            throw new ServiceException("Error with commit transaction", e);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    public void rollback() throws ServiceException {
        try {
            getConnection().rollback();
        } catch (SQLException e) {
            throw new ServiceException("Error with roolback transaction", e);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

}
