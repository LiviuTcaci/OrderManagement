# Orders Management System
A comprehensive Java application for managing warehouse orders, users, and products with a clean GUI interface, database integration, and real-time stock tracking.

## Overview

This Orders Management application helps businesses process client orders for warehouse products while maintaining inventory accuracy. Built with a layered architecture pattern, it separates concerns into distinct layers:

- Model layer (data models)
- Data access layer (DAO)
- Business logic layer
- Presentation layer (GUI)

## Features

- **User Management**: Add, edit, delete, and view user accounts
- **Product Management**: Track inventory with stock levels, prices, and product details
- **Order Processing**: Create orders with real-time stock validation
- **Bill Generation**: Automatically generate bills for completed orders
- **Activity Logging**: Keep records of all transactions
- **Dynamic Tables**: Reflection-based table generation for flexible data display

## Architecture

### Model Layer
- `User`: Represents system users with credentials and contact information
- `Product`: Represents inventory items with quantity and pricing
- `Order`: Represents customer orders linking users and products
- `Bill`: Immutable record of completed transactions

### Data Access Layer
- Data Access Objects (DAOs) handle CRUD operations for each entity type
- JDBC integration for database communication
- Transaction management for maintaining data integrity

### Presentation Layer
- Java Swing-based GUI with tabbed interface
- Separate panels for managing users, products, and orders
- Dynamic table displays using Java Reflection

## Technical Implementation

- **Layered Architecture**: Clean separation of concerns
- **Java Reflection**: Dynamic table generation and data binding
- **JDBC**: Database connectivity and operations
- **Design Patterns**: DAO pattern for database operations
- **Error Handling**: Robust validation and error management

## Database Setup

1. Execute the SQL script in `src/main/resources/console.sql` to create the database
2. Update database connection parameters in `util/ConnectionFactory.java` if needed:

```java
private static final String URL = "jdbc:mysql://localhost:3306/OrderManagementDB";
private static final String USER = "root";
private static final String PASSWORD = "password";
```

## Running the Application

1. Clone this repository
2. Set up the database using the provided SQL script
3. Build the project using Maven: `mvn clean install`
4. Run the main class: `ui.MainUI`

## Project Structure

```
.
├── src/main/java
│   ├── dao            # Data Access Objects
│   ├── model          # Data models
│   ├── ui             # User interface components
│   └── util           # Utilities and helpers
├── src/main/resources
│   └── console.sql    # Database setup script
└── src/test
    └── java           # Unit tests
```

## Testing

- JUnit tests are provided for core functionality validation
- Run tests with: `mvn test`

## Future Enhancements

- Advanced filtering and search capabilities
- Report generation and export functionality
- User authentication and role-based access control
- Enhanced UI with additional visual elements
