# Project Documentation

## Table of Contents

- [1. Project Overview](#1-Project-Overview)

- [2. Customer Requirements](#2-customer-requirements)

- [3. Development Methods](#3-development-methods)

- [4. System Design and Architecture](#4-system-design-and-architecture)

  - [4.1 Technologies Used](#41-Technologies-Used)
  
  - [4.2 Software Design](#42-Software-Design)

- [5. Implementation](#5-implementation)

    - [5.1 UI](#51-ui)

    - [5.2 Database](#52-database)

- [6. QA](#6-qa)

    - [6.1 Testing](#61-testing)

    - [6.2 Clean Code](#62-clean-code)

- [7. Deployment Process](#7-Deployment-Process)

- [8. Summary](#8-summary)



# 1. Project Overview





# 2. Customer Requirements

For inventory management system, the customer requires the following features:

### 2.1 Product Management
- **Add Product**  
  Capability to add new products to the inventory, including fields for:
  - Product name
  - Product price
  - Quantity
  - Category
  - Supplier


- **Delete Product**  
  Option to permanently remove products from the inventory when no longer needed.


- **Edit/Update Product**  
  Allow updates to:
  - Product name
  - Product price
  - Quantity
  - Category
  - Supplier
---

### 2.2 Product Search and Sorting
- **Search Products**  
  A search bar to quickly locate products by:
  - Product name


- **Sort Products in Tables**  
  Sort tables by:
  - Name
  - Price
  - Quantity
  - Supplier
  - Category
---

### 2.3 Reports
- **Generate Reports**

  Create inventory reports:
  - All stock products
  - Transactions by product ID
  - Transactions by user ID
  - Transactions by date
---

### 2.4 Transactions Page
- **View Transactions**  
  Display all transactions in a table with the following columns:
  - Transaction ID
  - Product ID
  - Transaction date
  - Quantity
  - Transaction type (Add, Update, Delete)
  - User that made the transaction
---

### 2.5 Reports
- **Generate Reports**  
  Create inventory reports:
  - All stock products
  - Transactions by product ID
  - Transactions by user ID
  - Transactions by date
---

### 2.6 User Management
- **User Authentication**  
  Login and register for users with:
  - Name
  - Username
  - Password
---
# 3. Development Methods



# 4. System Design and Architecture



## 4.1 Technologies Used



## 4.2 Software Design

### Introduction

This document will provide a detailed description of the system's design, including major classes, functions, and algorithms.

### Class Diagram

![Class Diagram](Diagrams/LuokkaKaavio.png)

### Authentication Flow

**1. User Login**: The user logs in with their username and password.

- The system checks the credentials against the stored user data.

**2. User Register**

- The user enters first name, last name, username, password.

**3. Token Generation**: On successful login, a token is generated.

- This token is used for secure access to user-specific data.

**4. Token Validation**: For each subsequent request, the token is validated to ensure the user is authenticated.

- If the token is valid, the request is processed; otherwise, an authentication error is returned.

#### Key Classes:
- [LoginController](/src/main/java/com/reppuhallinta/inventory_management_sys/controller/LoginController.java): Handles user login requests.
- [RegisterController](/src/main/java/com/reppuhallinta/inventory_management_sys/controller/RegisterController.java): Handles user registration requests.

### Product Management Flow

##### 1. Product Creation:

- The user fills in the product details (name, price, quantity, category, supplier) in the form.
- The system validates the input fields.
- The system retrieves the selected category and supplier IDs.
- The system parses and validates the product price and quantity.
- The system creates a new product using the provided details and logs the transaction.
  ![Product Creation Activity Diagram](Diagrams/AktiviteettiKaavio.png)

##### Key Classes:

- [CreateProductController](/src/main/java/com/reppuhallinta/inventory_management_sys/controller/CreateProductController.java): Handles the product creation form.
- [ProductService](/src/main/java/com/reppuhallinta/inventory_management_sys/service/ProductService.java): Manages product-related operations.
- [TransactionService](/src/main/java/com/reppuhallinta/inventory_management_sys/service/TransactionService.java): Manages transaction-related operations.

#### 2. Product Update:

- The user fills in the product details (name, price, quantity, category, supplier) in the form.
- The system validates the input fields.
- The system retrieves the selected category and supplier IDs.
- The system parses and validates the product price and quantity.
- The system creates a new product using the provided details and logs the transaction.

##### Key Classes:

- [EditProductController](/src/main/java/com/reppuhallinta/inventory_management_sys/controller/EditProductController.java): Handles the product update form.
- [ProductService](/src/main/java/com/reppuhallinta/inventory_management_sys/service/ProductService.java): Manages product-related operations.
- [TransactionService](/src/main/java/com/reppuhallinta/inventory_management_sys/service/TransactionService.java): Manages transaction-related operations.

#### 3. Product Deletion:

- The user selects a product from the list.
- The system retrieves the selected product ID.
- The system deletes the product from the database and logs the transaction.
- The system updates the product list.

##### Key Classes:

- [ProductViewController](/src/main/java/com/reppuhallinta/inventory_management_sys/controller/ProductViewController.java): Contains the method for product deletion.
- [ProductService](/src/main/java/com/reppuhallinta/inventory_management_sys/service/ProductService.java): Manages product-related operations.
- [TransactionService](/src/main/java/com/reppuhallinta/inventory_management_sys/service/TransactionService.java): Manages transaction-related operations.


## Transaction Management Flow

#### 1. Transaction Logging:
- The system logs all product-related transactions (creation, update, deletion).
- The system records the user who made the transaction, the product involved, and the type of transaction.
- The system timestamps each transaction for tracking purposes.
- The system stores the transaction data in the database for future reference.
- The system provides a transaction history view for users to track changes.
- The system allows users to filter transactions by date, user, or product.

##### Key Classes:
- [TransactionService](/src/main/java/com/reppuhallinta/inventory_management_sys/service/TransactionService.java): Manages transaction-related operations.
- [TransactionController](/src/main/java/com/reppuhallinta/inventory_management_sys/controller/TransactionController.java): Handles transaction history requests.

## Report Generation Flow

#### 1. Report Export:
- The user selects if they want to export all products in stock, transactions by product id, transaction by user id or transaction by date
- The system creates a text file with the selected data
- The system saves the file to the user's computer to selected location

##### Key Classes:
- [ReportController](/src/main/java/com/reppuhallinta/inventory_management_sys/controller/ReportViewController.java): Handles report generation requests.


# 5. Implementation



## 5.1 UI



## 5.2 Database

[Database Script](..%2Fsqlscript)

## Tables and Purpose

### 1. `Category`
- **Purpose**: Organizes products by category.
- **Columns**:
    - `Id`: Primary key.
    - `Name`: Unique category name.

---

### 2. `Suppliers`
- **Purpose**: Stores supplier details.
- **Columns**:
    - `SupplierID`: Primary key.
    - `Name`: Supplier name.
    - `ContactEmail`: Unique email.

---

### 3. `Products`
- **Purpose**: Maintains product details and links to suppliers and categories.
- **Columns**:
    - `ProductID`: Primary key.
    - `Name`: Product name.
    - `Price`: Decimal price.
    - `Quantity`: Stock count (not null).
    - `SupplierID`: Foreign key to `Suppliers`.
    - `CategoryId`: Foreign key to `Category`.
    - **Indexes**: `SupplierID`.

---

### 4. `Users`
- **Purpose**: Manages user authentication and roles.
- **Columns**:
    - `UserId`: Primary key.
    - `username`: Unique username.
    - `Password`: Encrypted (not null).
    - `AccessLevel`: User role.

---

### 5. `Transactions`
- **Purpose**: Logs product sales or restocking.
- **Columns**:
    - `TransactionID`: Primary key.
    - `ProductID`: Foreign key to `Products`.
    - `TransactionDate`: Timestamp.
    - `Quantity`: Transaction quantity.
    - `TransactionType`: Sale or restock.
    - `UserId`: Foreign key to `Users`.
    - **Indexes**: `ProductID`, `UserId`.

---

## Key Relationships
1. **Products ↔ Category**: Linked via `CategoryId`.
2. **Products ↔ Suppliers**: Linked via `SupplierID`.
3. **Transactions ↔ Products**: Linked via `ProductID`.
4. **Transactions ↔ Users**: Linked via `UserId`.

---

# 6. QA



## 6.1 Testing



## 6.2 Clean Code


# 7. Deployment Process


# 8. Summary

