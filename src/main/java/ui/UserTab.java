package ui;

import dao.UserDAO;
import model.User;
import util.ReflectionUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
/**
 * The User tab in the main user interface.
 * This class creates the User tab and handles the display of users.
 */
public class UserTab {
    private static JTable userTable;
    private static DefaultTableModel userTableModel;

    /**
     * Creates the User tab panel.
     * @return The User tab panel.
     */
    public static JPanel createUserPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        userTableModel = new DefaultTableModel();
        userTable = new JTable(userTableModel);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");

        addButton.addActionListener(e -> showAddUserDialog());
        editButton.addActionListener(e -> showEditUserDialog());
        deleteButton.addActionListener(e -> deleteSelectedUser());

        buttonsPanel.add(addButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);

        panel.add(new JScrollPane(userTable), BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);
        loadUserData();
        return panel;
    }

    /**
     * Loads user data from the database and displays it in the table.
     */
    public static void loadUserData() {
        List<User> users = new UserDAO().getAllUsers();
        DefaultTableModel newModel = ReflectionUtil.createTableModel(users);
        userTableModel.setDataVector(newModel.getDataVector(), ReflectionUtil.getColumnNames(User.class));
    }

    /**
     * Displays a dialog to add a new user.
     */
    private static void showAddUserDialog() {
        JTextField usernameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField passwordField = new JTextField(20);
        JTextField addressField = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add New User",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            UserDAO dao = new UserDAO();
            dao.addUser(new User(usernameField.getText(), emailField.getText(), passwordField.getText(), addressField.getText()));
            loadUserData();
        }
    }

    /**
     * Displays a dialog to edit a user.
     */
    private static void showEditUserDialog() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow >= 0) {
            int userId = Integer.parseInt(userTable.getValueAt(selectedRow, 0).toString());
            UserDAO dao = new UserDAO();
            User user = dao.findUserById(userId);

            JTextField usernameField = new JTextField(user.getUsername(), 20);
            JTextField emailField = new JTextField(user.getEmail(), 20);
            JTextField passwordField = new JTextField(user.getPassword(), 20);
            JTextField addressField = new JTextField(user.getAddress(), 20);

            JPanel panel = new JPanel(new GridLayout(0, 2));
            panel.add(new JLabel("Username:"));
            panel.add(usernameField);
            panel.add(new JLabel("Email:"));
            panel.add(emailField);
            panel.add(new JLabel("Password:"));
            panel.add(passwordField);
            panel.add(new JLabel("Address:"));
            panel.add(addressField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Edit User",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                dao.updateUser(new User(userId, usernameField.getText(), emailField.getText(), passwordField.getText(), addressField.getText()));
                loadUserData();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select a user to edit.");
        }
    }

    /**
     * Deletes the selected user from the database.
     */
    private static void deleteSelectedUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow >= 0) {
            int userId = Integer.parseInt(userTable.getValueAt(selectedRow, 0).toString());
            int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this user?", "Delete User", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new UserDAO().deleteUser(userId);
                loadUserData();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select a user to delete.");
        }
    }
}
