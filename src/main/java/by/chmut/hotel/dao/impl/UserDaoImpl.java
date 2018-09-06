package by.chmut.hotel.dao.impl;

import by.chmut.hotel.dao.AbstractDao;
import by.chmut.hotel.dao.UserDao;
import by.chmut.hotel.bean.User;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDaoImpl extends AbstractDao implements UserDao {

    private static final String selectUserByLogin = "SELECT Users.id,Users.login,Users.password,Users.name," +
            "Users.lastname,Users.role FROM Users WHERE Users.login = ?";
    private static final String selectUserById = "SELECT Users.id,Users.login,Users.password,Users.name,Users.lastname," +
            "Users.role,Contacts.email,Contacts.telephone,Contacts.country,Contacts.city,Contacts.address," +
            "Contacts.zip FROM Users,Contacts WHERE Users.contact_id = Contacts.id HAVING Users.id = ?";
    private static final String addContact = "INSERT INTO Contacts (email, telephone, country, city, address, zip) VALUES (?,?,?,?,?,?)";
    private static final String addUser = "INSERT INTO Users (login, password, name, lastname,role,contact_id) VALUES (?,?,?,?,?,?)";
    private static final String getContactID = "SELECT contact_id FROM Users WHERE id=?";
    private static final String updateUser = "UPDATE Contacts SET email=?, telephone=?, country=?, city=?, address=?, zip=? WHERE id=?";
    private static final String deleteUser = "DELETE FROM Users WHERE id=?";

    public User getUserByLogin(String login) throws SQLException {
        PreparedStatement ps = prepareStatement(selectUserByLogin);
        ps.setString(1,login);
        ResultSet rs = ps.executeQuery();
        User user = new User();
        while (rs.next()) {
            user.setId(rs.getInt(1));
            user.setLogin(rs.getString(2));
            user.setPassword(rs.getString(3));
            user.setName(rs.getString(4));
            user.setLastName(rs.getString(5));
            user.setRole(rs.getString(6));
        }
        return user;
    }

    @Override
    public User save(User user) throws SQLException {
        PreparedStatement psSave1 = prepareStatement(addContact,Statement.RETURN_GENERATED_KEYS);
        PreparedStatement psSave2 = prepareStatement(addUser,Statement.RETURN_GENERATED_KEYS);
        psSave1.setString(1,user.getEmail());
        psSave1.setString(2,user.getTelephone());
        psSave1.setString(3,user.getCountry());
        psSave1.setString(4,user.getCity());
        psSave1.setString(5,user.getAddress());
        psSave1.setString(6,user.getZip());
        psSave1.executeUpdate();
        ResultSet rs = psSave1.getGeneratedKeys();
        Integer contactId = 0;
        if (rs.next()) {
            contactId = (rs.getInt(1));
        }
        close(rs);
        psSave2.setString(1,user.getLogin());
        psSave2.setString(2,user.getPassword());
        psSave2.setString(3,user.getName());
        psSave2.setString(4,user.getLastName());
        psSave2.setString(5,user.getRole());
        psSave2.setInt(6,contactId);
        psSave2.executeUpdate();
        rs = psSave2.getGeneratedKeys();
        if (rs.next()) {
            user.setId(rs.getInt(1));
        }
        close(rs);
        return user;
    }

    @Override
    public User get(Serializable id) throws SQLException {
        PreparedStatement psGet = prepareStatement(selectUserById);
        psGet.setInt(1, (int)id);
        ResultSet rs = psGet.executeQuery();
        User user = new User();
        while (rs.next()) {
            user.setId(rs.getInt(1));
            user.setLogin(rs.getString(2));
            user.setPassword(rs.getString(3));
            user.setName(rs.getString(4));
            user.setLastName(rs.getString(5));
            user.setRole(rs.getString(6));
            user.setEmail(rs.getString(7));
            user.setTelephone(rs.getString(8));
            user.setCountry(rs.getString(9));
            user.setCity(rs.getString(10));
            user.setAddress(rs.getString(11));
            user.setZip(rs.getString(12));
        }
        return user;
    }

    @Override
    public void update(User user) throws SQLException {
        PreparedStatement psUpdate1 = prepareStatement(getContactID);
        PreparedStatement psUpdate2 = prepareStatement(updateUser);
        psUpdate1.setInt(1,user.getId());
        ResultSet rs = psUpdate1.executeQuery();
        Integer contactId = 0;
        if (rs.next()) {
            contactId = (rs.getInt(1));
        }
        close(rs);
        psUpdate2.setInt(7,contactId);
        psUpdate2.setString(1,user.getEmail());
        psUpdate2.setString(2,user.getTelephone());
        psUpdate2.setString(3,user.getCountry());
        psUpdate2.setString(4,user.getCity());
        psUpdate2.setString(5,user.getAddress());
        psUpdate2.setString(6,user.getZip());
        psUpdate2.executeUpdate();
    }

    @Override
    public int delete(Serializable id) throws SQLException {
        PreparedStatement psDelete = prepareStatement(deleteUser);
        psDelete.setInt(1,(int)id);
        return psDelete.executeUpdate();
    }


}
