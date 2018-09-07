package by.chmut.hotel.dao;

import java.io.Serializable;

public interface Dao<T> {

    T save(T t) throws DAOException;

    T get(Serializable id) throws DAOException;

    void update(T t) throws DAOException;

    int delete(Serializable id) throws DAOException;

}
