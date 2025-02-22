package dao;

import model.Bill;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Data Access Object for Log.
 * This class is responsible for performing CRUD operations on the Log table.
 */
public class LogDAO {
    private Connection connection;

    /**
     * Constructor for LogDAO.
     * Establishes a database connection.
     */
    public LogDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    /**
     * Inserts a new bill into the database.
     * @param bill The bill to be added.
     */
    public void insertBill(Bill bill) {
        String query = "INSERT INTO Log (order_id, user_id, user_name, product_id, product_name, quantity, total_price) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, bill.orderId());
            pst.setInt(2, bill.userId());
            pst.setString(3, bill.userName());
            pst.setInt(4, bill.productId());
            pst.setString(5, bill.productName());
            pst.setInt(6, bill.quantity());
            pst.setDouble(7, bill.totalPrice());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all bills from the database.
     * @return A list of all bills.
     */
    public List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();
        String query = "SELECT * FROM Log";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Bill bill = new Bill(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("total_price")
                );
                bills.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bills;
    }
}
