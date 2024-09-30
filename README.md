# Inventory-Management-Sys
University Project for Inventory Management system 


Product Vision
For small to medium-sized businesses who struggle with manual and error-prone inventory
management, the [Inventory Management App] is a comprehensive digital solution that provides
real-time tracking, automated alerts, and reports/charts. Unlike traditional spreadsheets or
outdated software, our app offers an intuitive interface and advanced analytics, making
inventory management more accurate, efficient, and strategic.


Description of ER Diagram Entities and Relationships:
● Users: Represents the users who interact with the system. Each user has attributes like
username, first name, last name, password, access level, and UserID (primary key).
● Products: Represents the products in the inventory. Each product has attributes like
ProductID (primary key), name, price, quantity, category, and supplier. Products are
linked to Categories and Suppliers through foreign keys.
● Suppliers: Contains supplier information, including SupplierID (primary key), name, and
contact email.
● Category: Holds product categories. Each category has a CategoryID (primary key) and
name.
● Transactions: Tracks transactions performed on the products by users. Each
transaction includes attributes such as TransactionID (primary key), transaction date,
quantity, transaction type (e.g., sale or return), ProductID, and UserID (foreign keys to
link with the Products and Users tables).

![image](https://github.com/user-attachments/assets/3c305bc0-eafb-402d-9f24-f49847513607)
