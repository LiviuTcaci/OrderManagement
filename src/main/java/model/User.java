package model;
/**
 * Represents a user in the system.
 * Each user has a username, email, password, and address.
 */
public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String address;

    /**
     * Constructor for creating a user with all details.
     * @param id The user's ID.
     * @param username The user's username.
     * @param email The user's email.
     * @param password The user's password.
     * @param address The user's address.
     */
    public User(int id, String username, String email, String password, String address) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
    }
    /**
     * Constructor for creating a user without an ID (for new users).
     * @param username The user's username.
     * @param email The user's email.
     * @param password The user's password.
     * @param address The user's address.
     */
    public User(String username, String email, String password, String address) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.address = address;
    }
    /**
     * Gets the user's ID.
     * @return The user's ID.
     */
    public int getId() {
        return id;
    }
    /**
     * Sets the user's ID.
     * @param id The user's ID.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Gets the user's username.
     * @return The user's username.
     */
    public String getUsername() {
        return username;
    }
    /**
     * Sets the user's username.
     * @param username The user's username.
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Gets the user's email.
     * @return The user's email.
     */
    public String getEmail() {
        return email;
    }
    /**
     * Sets the user's email.
     * @param email The user's email.
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Gets the user's password.
     * @return The user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the user's address.
     * @return The user's address.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Returns a string representation of the user.
     * @return The user's ID and username.
     */
    @Override
    public String toString() {
        return String.format("ID: %d, Username: %s", id, username);
    }
}
