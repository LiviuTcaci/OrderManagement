package ui;

import dao.LogDAO;
import model.Bill;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The main user interface for the Order Management System.
 * This class creates the main window and adds tabs for Products, Users, and Orders.
 */
public class MainUI {
    private JFrame frame;
    private JTabbedPane tabbedPane;


    /**
     * Constructor for MainUI.
     * Initializes the main user interface.
     */
    public MainUI() {
        initializeUI();
    }

    /**
     * Initializes the main user interface.
     */
    private void initializeUI() {
        frame = new JFrame("Order Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Products", ProductTab.createProductPanel());
        tabbedPane.addTab("Users", UserTab.createUserPanel());
        tabbedPane.addTab("Orders", OrderTab.createOrderPanel());

        frame.add(tabbedPane, BorderLayout.CENTER);

        JButton showLogsButton = new JButton("Show Logs");
        showLogsButton.addActionListener(e -> showLogs());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(showLogsButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    /**
     * Displays all log entries in a new window.
     */
    private void showLogs() {
        LogDAO logDAO = new LogDAO();
        List<Bill> bills = logDAO.getAllBills();

        StringBuilder logContent = new StringBuilder();
        for (Bill bill : bills) {
            logContent.append(bill).append("\n");
        }

        JTextArea textArea = new JTextArea(logContent.toString());
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);

        JFrame logFrame = new JFrame("Log Entries");
        logFrame.setSize(600, 400);
        logFrame.add(scrollPane);
        logFrame.setVisible(true);
    }

    /**
     * The main method that starts the application.
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainUI::new);
    }
}
