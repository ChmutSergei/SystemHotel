package by.chmut.hotel.dao;

import java.io.Serializable;
import java.sql.SQLException;

public interface Dao<T> {
    T save(T t) throws SQLException;
    T get(Serializable id) throws SQLException;
    void update(T t) throws SQLException;
    int delete(Serializable id) throws SQLException;
}
