package ui;

import dao.ProductDAO;
import model.Product;
import util.ReflectionUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * The Product tab in the main user interface.
 * This class creates the Product tab and handles the display of products.
 */
public class ProductTab {
    private static JTable productTable;
    private static DefaultTableModel productTableModel;

    /**
     * Creates the Product tab panel.
     * @return The Product tab panel.
     */
    public static JPanel createProductPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        productTableModel = new DefaultTableModel();
        productTable = new JTable(productTableModel);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        addButton.addActionListener(e -> showAddProductDialog());
        editButton.addActionListener(e -> showEditProductDialog());
        deleteButton.addActionListener(e -> deleteSelectedProduct());

        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);

        panel.add(new JScrollPane(productTable), BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);
        loadProductData();
        return panel;
    }

    /**
     * Loads product data from the database and displays it in the table.
     */
    public static void loadProductData() {
        List<Product> products = new ProductDAO().getAllProducts();
        DefaultTableModel newModel = ReflectionUtil.createTableModel(products);
        productTableModel.setDataVector(newModel.getDataVector(), ReflectionUtil.getColumnNames(Product.class));
    }

    /**
     * Displays a dialog to add a new product.
     */
    private static void showAddProductDialog() {
        JTextField nameField = new JTextField(20);
        JTextField quantityField = new JTextField(20);
        JTextField priceField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add New Product",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if (!isNumeric(quantityField.getText()) || !isNumeric(priceField.getText())) {
                JOptionPane.showMessageDialog(null, "Quantity and Price must be numeric.");
                return;
            }
            ProductDAO dao = new ProductDAO();
            dao.addProduct(new Product(nameField.getText(), Integer.parseInt(quantityField.getText()), Double.parseDouble(priceField.getText())));
            loadProductData();
        }
    }

    /**
     * Displays a dialog to edit a product.
     */
    private static void showEditProductDialog() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            int productId = Integer.parseInt(productTable.getValueAt(selectedRow, 0).toString());
            ProductDAO dao = new ProductDAO();
            Product product = dao.findProductById(productId);

            JTextField nameField = new JTextField(product.getName(), 20);
            JTextField quantityField = new JTextField(String.valueOf(product.getQuantity()), 20);
            JTextField priceField = new JTextField(String.valueOf(product.getPrice()), 20);

            JPanel panel = new JPanel(new GridLayout(0, 2));
            panel.add(new JLabel("Name:"));
            panel.add(nameField);
            panel.add(new JLabel("Quantity:"));
            panel.add(quantityField);
            panel.add(new JLabel("Price:"));
            panel.add(priceField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Edit Product",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                dao.updateProduct(new Product(productId, nameField.getText(), Integer.parseInt(quantityField.getText()), Double.parseDouble(priceField.getText())));
                loadProductData();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select a product to edit.");
        }
    }

    /**
     * Deletes the selected product from the database.
     */
    private static void deleteSelectedProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            int productId = Integer.parseInt(productTable.getValueAt(selectedRow, 0).toString());
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this product?", "Delete Product", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new ProductDAO().deleteProduct(productId);
                loadProductData();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select a product to delete.");
        }
    }

    /**
     * Checks if a string is numeric.
     * @param str The string to check.
     * @return True if the string is numeric, false otherwise.
     */
    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
