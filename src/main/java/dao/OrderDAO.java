package dao;

import model.Bill;
import model.Order;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Order.
 * This class is responsible for performing CRUD operations on the Order table.
 */
public class OrderDAO {
    private Connection connection;
    /**
     * Constructor for OrderDAO.
     * Establishes a database connection.
     */
    public OrderDAO() {
        this.connection = ConnectionFactory.getConnection();
    }
    /**
     * Inserts a new order into the database.
     * @param order The order to be added.
     */
    public void addOrder(Order order) {
        String query = "INSERT INTO Orders (id, user_id, product_id, quantity, total_price) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, order.getId());
            pst.setInt(2, order.getUserId());
            pst.setInt(3, order.getProductId());
            pst.setInt(4, order.getQuantity());
            pst.setDouble(5, order.getTotalPrice());
            pst.executeUpdate();

            // Insert bill into log
            Bill bill = new Bill(order.getId(), order.getUserId(), order.getUserName(), order.getProductId(), order.getProductName(), order.getQuantity(), order.getTotalPrice());
            insertBill(bill);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Updates an existing order in the database.
     * @param newOrder The order with updated details.
     * @param oldOrder The order with the original details.
     */
    public void updateOrder(Order newOrder, Order oldOrder) {
        String query = "UPDATE Orders SET id = ?, user_id = ?, product_id = ?, quantity = ?, total_price = ? WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, newOrder.getId());
            pst.setInt(2, newOrder.getUserId());
            pst.setInt(3, newOrder.getProductId());
            pst.setInt(4, newOrder.getQuantity());
            pst.setDouble(5, newOrder.getTotalPrice());
            pst.setInt(6, oldOrder.getId());
            pst.executeUpdate();

            // Adjust product quantity
            adjustProductQuantity(newOrder, oldOrder);

            // Insert bill into log
            Bill bill = new Bill(newOrder.getId(), newOrder.getUserId(), newOrder.getUserName(), newOrder.getProductId(), newOrder.getProductName(), newOrder.getQuantity(), newOrder.getTotalPrice());
            insertBill(bill);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adjusts the quantity of the product in the database.
     * @param newOrder The order with updated details.
     * @param oldOrder The order with the original details.
     */
    private void adjustProductQuantity(Order newOrder, Order oldOrder) {
        ProductDAO productDAO = new ProductDAO();
        if (newOrder.getProductId() == oldOrder.getProductId()) {
            int quantityDifference = newOrder.getQuantity() - oldOrder.getQuantity();
            productDAO.updateProductQuantity(newOrder.getProductId(), productDAO.findProductById(newOrder.getProductId()).getQuantity() - quantityDifference);
        } else {
            productDAO.updateProductQuantity(oldOrder.getProductId(), productDAO.findProductById(oldOrder.getProductId()).getQuantity() + oldOrder.getQuantity());
            productDAO.updateProductQuantity(newOrder.getProductId(), productDAO.findProductById(newOrder.getProductId()).getQuantity() - newOrder.getQuantity());
        }
    }

    /**
     * Deletes an order from the database.
     * @param orderId The ID of the order to be deleted.
     */
    public void deleteOrder(int orderId) {
        String query = "DELETE FROM Orders WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, orderId);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves an order from the database by its ID.
     * @param id The ID of the order to be retrieved.
     * @return The order if found, null otherwise.
     */
    public Order findOrderById(int id) {
        String query = "SELECT o.id, o.user_id, u.username AS user_name, o.product_id, p.name AS product_name, o.quantity, o.total_price " +
                "FROM Orders o " +
                "JOIN User u ON o.user_id = u.id " +
                "JOIN Product p ON o.product_id = p.id " +
                "WHERE o.id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Order(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("total_price")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all orders from the database.
     * @return A list of all orders.
     */
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT o.id, o.user_id, u.username AS user_name, o.product_id, p.name AS product_name, o.quantity, o.total_price " +
                "FROM Orders o " +
                "JOIN User u ON o.user_id = u.id " +
                "JOIN Product p ON o.product_id = p.id";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("total_price")
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    /**
     * Inserts a new bill into the database.
     * @param bill The bill to be added.
     */
    private void insertBill(Bill bill) {
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
}
