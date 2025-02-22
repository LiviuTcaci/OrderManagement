package dao;

import model.User;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Data Access Object for User.
 * This class is responsible for performing CRUD operations on the User table.
 */
public class UserDAO {
    private Connection connection;
    /**
     * Constructor for UserDAO.
     * Establishes a database connection.
     */
    public UserDAO() {
        this.connection = ConnectionFactory.getConnection();
    }
    /**
     * Inserts a new user into the database.
     * @param user The user to be added.
     */
    public void addUser(User user) {
        String query = "INSERT INTO User (username, email, password, address) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getPassword());
            pst.setString(4, user.getAddress());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Updates an existing user in the database.
     * @param user The user with updated details.
     */
    public void updateUser(User user) {
        String query = "UPDATE User SET username = ?, email = ?, password = ?, address = ? WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getEmail());
            pst.setString(3, user.getPassword());
            pst.setString(4, user.getAddress());
            pst.setInt(5, user.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Deletes a user from the database.
     * @param userId The ID of the user to be deleted.
     */
    public void deleteUser(int userId) {
        String query = "DELETE FROM User WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, userId);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Retrieves all users from the database.
     * @param id The ID of the user to be retrieved.
     * @return A list of all users.
     *
     */
    public User findUserById(int id) {
        String query = "SELECT * FROM User WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("address")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Retrieves all users from the database.
     * @return A list of all users.
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM User";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("address")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
