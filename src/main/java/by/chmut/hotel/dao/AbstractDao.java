package by.chmut.hotel.dao;

import by.chmut.hotel.dao.database.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractDao {

    protected PreparedStatement prepareStatement(String query) throws DAOException {

        try {
            return ConnectionManager.getConnection().prepareStatement(query);
        } catch (SQLException e) {
            throw new DAOException("Error with PreparedStatement",e);
        }
    }

    protected PreparedStatement prepareStatement(String query, int returnKey) throws DAOException {

        try {
            return ConnectionManager.getConnection().prepareStatement(query, returnKey);
        } catch (SQLException e) {
            throw new DAOException("Error with PreparedStatement",e);
        }
    }

    protected void close(ResultSet rs) throws DAOException {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            throw new DAOException("Error with close ResultSet",e);
        }
    }
}
