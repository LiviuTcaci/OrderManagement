CREATE DATABASE OrderManagementDB;
USE OrderManagementDB;

CREATE TABLE Product (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255),
                         quantity INT,
                         price DECIMAL(10, 2)
);

CREATE TABLE User (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      username VARCHAR(255),
                      email VARCHAR(255),
                      password VARCHAR(255),
                      address TEXT
);

CREATE TABLE Orders (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        user_id INT,
                        product_id INT,
                        quantity INT,
                        total_price DECIMAL(10, 2),
                        FOREIGN KEY (user_id) REFERENCES User(id),
                        FOREIGN KEY (product_id) REFERENCES Product(id)
);

CREATE TABLE Log (
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     order_id INT,
                     user_id INT,
                     user_name VARCHAR(255),
                     product_id INT,
                     product_name VARCHAR(255),
                     quantity INT,
                     total_price DECIMAL(10, 2),
                     FOREIGN KEY (order_id) REFERENCES Orders(id)
);


INSERT INTO Product (name, quantity, price) VALUES
                                                ('Laptop', 10, 999.99),
                                                ('Smartphone', 15, 499.49),
                                                ('Headphones', 30, 59.99);


INSERT INTO User (username, email, password, address) VALUES
                                                          ('johndoe', 'john.doe@example.com', 'password123', '1234 Elm Street'),
                                                          ('janedoe', 'jane.doe@example.com', 'password123', '5678 Oak Street');

-- Note: Ensure to use existing user and product IDs when inserting into the Order table
INSERT INTO Orders (user_id, product_id, quantity, total_price) VALUES (1, 1, 1, 999.99),
                                                                   (2, 2, 2, 998.98);



