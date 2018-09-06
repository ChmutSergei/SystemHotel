package by.chmut.hotel.dao;

import by.chmut.hotel.dao.database.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractDao {

    protected PreparedStatement prepareStatement(String query) throws SQLException {
        return ConnectionManager.getConnection().prepareStatement(query);
    }

    protected PreparedStatement prepareStatement(String query, int returnKey) throws SQLException {
        return ConnectionManager.getConnection().prepareStatement(query, returnKey);
    }

    protected void close(ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
