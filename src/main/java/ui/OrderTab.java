package ui;

import dao.OrderDAO;
import dao.ProductDAO;
import dao.UserDAO;
import model.Order;
import model.Product;
import model.User;
import util.ReflectionUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * The Order tab in the main user interface.
 * This class creates the Order tab and handles the display of orders.
 */
public class OrderTab {
    private static JTable orderTable;
    private static DefaultTableModel orderTableModel;
    private static List<Integer> deletedOrderIds = new ArrayList<>(); // Track deleted order IDs

    /**
     * Creates the Order tab panel.
     * @return The Order tab panel.
     */
    public static JPanel createOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        orderTableModel = new DefaultTableModel();
        orderTable = new JTable(orderTableModel);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        addButton.addActionListener(e -> showAddOrderDialog());
        editButton.addActionListener(e -> showEditOrderDialog());
        deleteButton.addActionListener(e -> deleteSelectedOrder());

        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);

        panel.add(new JScrollPane(orderTable), BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);
        loadOrderData();
        return panel;
    }

    /**
     * Loads order data from the database and displays it in the table.
     */
    public static void loadOrderData() {
        List<Order> orders = new OrderDAO().getAllOrders();
        DefaultTableModel newModel = ReflectionUtil.createTableModel(orders);
        orderTableModel.setDataVector(newModel.getDataVector(), new Vector<>(List.of("ID", "User ID", "User Name", "Product ID", "Product Name", "Quantity", "Total Price")));
    }

    /**
     * Shows a dialog to add a new order.
     */
    private static void showAddOrderDialog() {
        JComboBox<Product> productComboBox = new JComboBox<>(new Vector<>(new ProductDAO().getAllProducts()));
        JComboBox<User> userComboBox = new JComboBox<>(new Vector<>(new UserDAO().getAllUsers()));
        JTextField orderIdField = new JTextField(20);
        JTextField quantityField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Order ID:"));
        panel.add(orderIdField);
        panel.add(new JLabel("User:"));
        panel.add(userComboBox);
        panel.add(new JLabel("Product:"));
        panel.add(productComboBox);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add New Order",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            User user = (User) userComboBox.getSelectedItem();
            Product product = (Product) productComboBox.getSelectedItem();
            int orderId = Integer.parseInt(orderIdField.getText());
            int quantity = Integer.parseInt(quantityField.getText());

            if (product.getQuantity() < quantity) {
                JOptionPane.showMessageDialog(null, "Insufficient stock.");
                return;
            }

            double totalPrice = product.getPrice() * quantity;
            new OrderDAO().addOrder(new Order(orderId, user.getId(), user.getUsername(), product.getId(), product.getName(), quantity, totalPrice));
            new ProductDAO().updateProductQuantity(product.getId(), product.getQuantity() - quantity);
            loadOrderData();
            ProductTab.loadProductData(); // Refresh product data in the Product tab
        }
    }

    /**
     * Shows a dialog to edit an existing order.
     */
    private static void showEditOrderDialog() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow >= 0) {
            int oldOrderId = Integer.parseInt(orderTable.getValueAt(selectedRow, 0).toString());
            OrderDAO dao = new OrderDAO();
            Order oldOrder = dao.findOrderById(oldOrderId);

            JComboBox<Product> productComboBox = new JComboBox<>(new Vector<>(new ProductDAO().getAllProducts()));
            JComboBox<User> userComboBox = new JComboBox<>(new Vector<>(new UserDAO().getAllUsers()));
            productComboBox.setSelectedItem(new ProductDAO().findProductById(oldOrder.getProductId()));
            userComboBox.setSelectedItem(new UserDAO().findUserById(oldOrder.getUserId()));
            JTextField orderIdField = new JTextField(String.valueOf(oldOrder.getId()), 20);
            JTextField quantityField = new JTextField(String.valueOf(oldOrder.getQuantity()), 20);

            JPanel panel = new JPanel(new GridLayout(0, 2));
            panel.add(new JLabel("Order ID:"));
            panel.add(orderIdField);
            panel.add(new JLabel("User:"));
            panel.add(userComboBox);
            panel.add(new JLabel("Product:"));
            panel.add(productComboBox);
            panel.add(new JLabel("Quantity:"));
            panel.add(quantityField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Edit Order",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                User user = (User) userComboBox.getSelectedItem();
                Product product = (Product) productComboBox.getSelectedItem();
                int newOrderId = Integer.parseInt(orderIdField.getText());
                int newQuantity = Integer.parseInt(quantityField.getText());

                int availableStock = product.getQuantity() + oldOrder.getQuantity(); // Include the old order quantity in available stock
                if (availableStock < newQuantity) {
                    JOptionPane.showMessageDialog(null, "Insufficient stock.");
                    return;
                }

                double totalPrice = product.getPrice() * newQuantity;
                Order newOrder = new Order(newOrderId, user.getId(), user.getUsername(), product.getId(), product.getName(), newQuantity, totalPrice);
                dao.updateOrder(newOrder, oldOrder);
                loadOrderData();
                ProductTab.loadProductData(); // Refresh product data in the Product tab
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select an order to edit.");
        }
    }

    /**
     * Deletes the selected order from the database.
     */
    private static void deleteSelectedOrder() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow >= 0) {
            int orderId = Integer.parseInt(orderTable.getValueAt(selectedRow, 0).toString());
            String[] options = {"Declined", "Finished", "Cancel"};
            int status = JOptionPane.showOptionDialog(null, "Was the order declined, finished or cancel?",
                    "Order Status", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                    null, options, options[0]);
            if (status != 2) { // If not canceled
                Order order = new OrderDAO().findOrderById(orderId);
                if (status == 0) { // Declined
                    Product product = new ProductDAO().findProductById(order.getProductId());
                    new ProductDAO().updateProductQuantity(order.getProductId(), product.getQuantity() + order.getQuantity());
                }
                new OrderDAO().deleteOrder(orderId);
                deletedOrderIds.add(orderId); // Add to deleted IDs
                loadOrderData();
                ProductTab.loadProductData(); // Refresh product data in the Product tab
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select an order to delete.");
        }
    }


}
