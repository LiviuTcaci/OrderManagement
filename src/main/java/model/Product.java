package model;
/**
 * Represents a product in the system.
 * Each product has a name, quantity, and price.
 */
public class Product {
    private int id;
    private String name;
    private int quantity;
    private double price;
    /**
     * Constructor for creating a product with all details.
     * @param id The product's ID.
     * @param name The product's name.
     * @param quantity The quantity of the product in stock.
     * @param price The price of the product.
     */
    public Product(int id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    /**
     * Constructor for creating a product without an ID (for new products).
     * @param name The product's name.
     * @param quantity The quantity of the product in stock.
     * @param price The price of the product.
     */
    public Product(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Gets the product's ID.
     * @return The product's ID.
     */
    public int getId() {
        return id;
    }
    /**
     * Gets the product's name.
     * @return The product's name.
     */
    public String getName() {
        return name;
    }
    /**
     * Gets the quantity of the product in stock.
     * @return The quantity of the product in stock.
     */
    public int getQuantity() {
        return quantity;
    }
    /**
     * Gets the price of the product.
     * @return The price of the product.
     */
    public double getPrice() {
        return price;
    }
    /**
     * Sets the product's ID.
     * @param id The product's ID.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Sets the product's name.
     * @param name The product's name.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Sets the quantity of the product in stock.
     * @param quantity The quantity of the product in stock.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns a string representation of the product.
     * @return The product's ID and name.
     */
    @Override
    public String toString() {
        return String.format("ID: %d, Name: %s", id, name);
    }
}
