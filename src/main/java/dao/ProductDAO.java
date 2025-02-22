package dao;

import model.Product;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Product.
 * This class is responsible for performing CRUD operations on the Product table.
 */
public class ProductDAO {
    private Connection connection;

    /**
     * Constructor for ProductDAO.
     * Establishes a database connection.
     */
    public ProductDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    /**
     * Inserts a new product into the database.
     * @param product The product to be added.
     */
    public void addProduct(Product product) {
        String query = "INSERT INTO Product (name, quantity, price) VALUES (?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, product.getName());
            pst.setInt(2, product.getQuantity());
            pst.setDouble(3, product.getPrice());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all products from the database.
     * @return A list of all products.
     */
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Product";
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    /**
     * Updates an existing product in the database.
     * @param product The product with updated details.
     */
    public void updateProduct(Product product) {
        String query = "UPDATE Product SET name = ?, quantity = ?, price = ? WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, product.getName());
            pst.setInt(2, product.getQuantity());
            pst.setDouble(3, product.getPrice());
            pst.setInt(4, product.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a product from the database.
     * @param productId The ID of the product to be deleted.
     */
    public void deleteProduct(int productId) {
        String query = "DELETE FROM Product WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, productId);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    /**
     * Retrieves a product from the database by its ID.
     * @param id The ID of the product to be retrieved.
     * @return The product if found, null otherwise.
     */
    public Product findProductById(int id) {
        String query = "SELECT * FROM Product WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates the quantity of a product in the database.
     * @param productId The ID of the product to be updated.
     * @param newQuantity The new quantity of the product.
     */
    public void updateProductQuantity(int productId, int newQuantity) {
        String query = "UPDATE Product SET quantity = ? WHERE id = ?";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, newQuantity);
            pst.setInt(2, productId);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

