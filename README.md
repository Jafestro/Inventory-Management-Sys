# Inventory-Management-Sys
School project


 ## Project Overview:

This project is a simple and efficient inventory management system designed to help users manage their item catalogs. 
The system allows for easy tracking, updating, and management of inventory, ensuring smooth operations for various user needs.

## Main Features

**Add, delete, edit products in the inventory**

**Track transactions by product, and see whos made changes**

**Automated reorder from supplier if wanted**

**Reports exported to computer from your stock changes**

**Make your users either admins or normal users, admins with more permissions**

## Getting started

1. **Clone the repository:**
    ```sh
    git clone https://github.com/Jafestro/Inventory-Management-Sys.git
    ```

2. **Create database**
   ```sh
   create database {yourdatabasename}
   use database {yourdatabasename}
   ```
   After that use [THIS](https://github.com/Jafestro/Inventory-Management-Sys/blob/main/sqlscript) sql script to add needed tables and columns

4. **Set up database connection**
   In the project search for file application.properties and fill in your correct database info to connect

5. **Starting the app**
   Run these commands
   ```sh
   mvn clean install
   mvn clean javafx:run
   ```
   
## Technologies Used

  - Java
  - Spring Boot
  - Mavent
  - JavaFX
  - MariaDB
  - JUnit

![image](https://github.com/user-attachments/assets/3c305bc0-eafb-402d-9f24-f49847513607)
