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





## 2. Customer Requirements

For invetory management system, the customer requires the following features:

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

