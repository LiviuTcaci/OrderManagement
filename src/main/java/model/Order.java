package model;

import dao.ProductDAO;
/**
 * Represents an order in the system.
 * Each order is associated with a user and a product.
 */
public class Order {
    private int id;
    private int userId;
    private String userName;
    private int productId;
    private String productName;
    private int quantity;
    private double totalPrice;

    /**
     * Constructor for creating an order with an ID (for existing orders).
     * @param id The order's ID.
     * @param userId The ID of the user who made the order.
     * @param userName The user's name.
     * @param productId The ID of the product ordered.
     * @param productName The product name.
     * @param quantity The quantity of the product ordered.
     * @param totalPrice The total price of the order.
     */
    public Order(int id, int userId, String userName, int productId, String productName, int quantity, double totalPrice) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
    /**
     * Constructor for creating an order without an ID (for new orders).
     * @param userId The ID of the user who made the order.
     * @param productId The ID of the product ordered.
     * @param quantity The quantity of the product ordered.
     * @param totalPrice The total price of the order.
     */
    public Order(int userId, int productId, int quantity, double totalPrice) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
    /**
     * Gets the order's ID.
     * @return The order's ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the order's ID.
     * @param id The order's ID.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Gets the ID of the user who made the order.
     * @return The ID of the user who made the order.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Gets the ID of the product ordered.
     * @return The ID of the product ordered.
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Gets the quantity of the product ordered.
     * @return The quantity of the product ordered.
     */
    public int getQuantity() {
        return quantity;
    }
    /**
     * Sets the quantity of the product ordered.
     * @param quantity The quantity of the product ordered.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    /**
     * Gets the total price of the order.
     * @return The total price of the order.
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Gets the user's name.
     * @return The user's name.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Gets the product name.
     * @return The product name.
     */
    public String getProductName() {
        return productName;
    }


    /**
     * Returns a string representation of the order.
     * @return A string representation of the order.
     */
    @Override
    public String toString() {
        return String.format("ID: %d, User: %s, Product: %s, Quantity: %d, Total Price: %.2f", id, userName, productName, quantity, totalPrice);
    }
}