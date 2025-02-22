package model;

/**
 * Represents a bill for an order.
 * A Bill object is immutable and contains details about the order.
 */
public record Bill(int orderId, int userId, String userName, int productId, String productName, int quantity, double totalPrice) {
}
