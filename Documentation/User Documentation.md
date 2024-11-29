# User Documentation

- [Purpose](#purpose)
- [Audience](#Audience)
- [Examples](#Examples)
  - [Installation](#Installation)
  - [Tutorials](#Tutorials)
- [Key Features](#Key-Features)
    - [Features](#Features)
    - [Step by Step](#Instructions)

## Purpose
- The purpose of this Inventory Management System is to provide a straightforward and highly efficient solution for users to manage their item catalogs effectively. Whether dealing with a small collection of items or a larger inventory, this system is designed to streamline the process, ensuring users can easily add, update, and delete items as needed. It enables users to maintain accurate records, track quantities, and categorize their inventory, making it easier to stay organized and meet their management needs. By simplifying these tasks, the system not only saves time but also reduces the likelihood of errors, allowing users to focus on other important aspects of their work or business operations. This tool is ideal for anyone seeking an intuitive and reliable way to keep their inventory well-structured and accessible.

## Audience
- This application is specifically designed for users who require a practical and user-friendly solution to manage their inventory efficiently and effectively. Whether you are an individual, a small to medium-sized business owner, or part of an organization, this tool offers the flexibility and functionality needed to keep your stock well-organized and up to date. It is particularly well-suited for businesses that deal with retail, wholesale, or service industries, as well as schools, non-profit organizations, or other institutions that need to maintain accurate records of supplies, equipment, or resources. By providing features that support streamlined inventory tracking, categorization, and management, the application helps reduce manual effort and minimizes the risk of errors. Its adaptability makes it an excellent choice for various scenarios where inventory control is essential, empowering users to focus on their core activities with confidence and ease.

## Examples



### Installation
**1. Clone the repository**
```sh
git clone https://github.com/Jafestro/Inventory-Management-Sys.git
```

**2. Setup database**

You can use for example MariaDB/MySQL.

You can get sql script from here:

[SQL SCRIPT](../sqlscript)

**3. Setup database connection in code**
Navigate to following file in code:
```sh
src/main/resources/application.properties
```

Copy the following and replace the placeholders with your database credentials:

Replace the file in src/main/resources/application.properties with the following:
```sh 
 spring.application.name=inventory_management_sys

spring.datasource.url=jdbc:mariadb://localhost:3306
spring.datasource.username={YOUR DATABASE USERNAME}
spring.datasource.password={YOUR DATABASE PASSWORD}
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update

# Custom properties for schemas
app.datasource.schema.default=jdbc:mariadb://localhost:3306/invsys

# Show SQL queries in the console
spring.jpa.show-sql=true

# Use PhysicalNamingStrategyStandardImpl to respect table and column names
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
```

**4. Run the application**
```sh
mvn clean install
```

**5. Start the application**
```sh
mvn clean javafx:run
```

**IF YOU ARE USING INTELLIJ YOU MIGHT HAVE TO ADD JAVAFX SDK FILE**

[How to add JavaFX SDK to IntelliJ IDEA](https://www.jetbrains.com/help/idea/sdk.html)

# Tutorials
## Login Screen

![Login Screen](Screenshots/Login.png)

Here you can login to the application.  
If you don't have an account, you can create one by clicking the "Are you not registered yet" button.

---

## Register Screen

![Register Screen](Screenshots/Register.png)

Here you can register a new account.  
Fill in the required fields and click the "Register" button.

---

## Products Screen

![Products Screen](Screenshots/Products.png)

Here you can view all products.  
On the left, you can manage different things by using the buttons.

---

## Transactions Screen

![Transactions Screen](Screenshots/Transactions.png)

Here you can view all transactions.  
On the left, you can manage different things by using the buttons.

---

## Reports Screen

![Reports Screen](Screenshots/Reports.png)

Here you can export different reports.  
You can get to this page by clicking the "Reports" button on the left in the products view.

---

## Edit Product Screen

![Edit Product Screen](Screenshots/Edit%20Product.png)

Here you can edit a product.  
You can get to this window from the products view by selecting a product and then clicking the "Edit product" button.

---

## Edit Transaction Screen

![Edit Transaction Screen](Screenshots/Edit%20Transaction.png)

Here you can edit a transaction.  
You can get to this window from the transactions view by selecting a transaction and then clicking the "Edit transaction" button.

---

## Create Product Screen

![Create Product Screen](Screenshots/Create%20Product.png)

Here you can create a product.
You can get to this window from the products view by clicking the "Create product" button.



## Key-Features


### Features
1. Product Management:
  - Create, update, and delete products.
  - View a list of all products with detailed information including price, quantity, category, and supplier.
2. Category and Supplier Management:
  - Add new categories and suppliers.
  - View and select categories and suppliers when creating or updating products.
3. Transaction Tracking:
  - Automatic logging of transactions (add, update) for products.
  - View transaction history for auditing purposes.

### Instructions
