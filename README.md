<p align="center">
  <img src="https://github.com/user-attachments/assets/bb2f3fc5-e002-49c5-8acc-48829643e81c" alt="V (4)" style="max-width: 100%; height: auto;" />
</p>

# <p align="center">Vectra Vault Inc.</p>

# Inventory-Management-Sys
School project


 ## Project Overview:

This project is a simple and efficient inventory management system designed to help users manage their item catalogs. 
The system allows for easy tracking, updating, and management of inventory, ensuring smooth operations for various user needs.

## Main Features


- Add, delete, edit products in the inventory

- Track transactions by product, and see whos made changes

- Automated reorder from supplier if wanted

- Reports exported to computer from your stock changes

- Make your users either admins or normal users, admins with more permissions


## Getting started

1. **Clone the repository:**
    ```sh
    git clone https://github.com/Jafestro/Inventory-Management-Sys.git
    ```

2. **Create database**
   ```sh
   create database {yourdatabasename}
   ```
   ```sh
   use database {yourdatabasename}
   ```
   After that use [THIS](https://github.com/Jafestro/Inventory-Management-Sys/blob/main/sqlscript) sql script to add needed tables and columns

3. **Set up database connection**
   
   In the project search for file application.properties and fill in your correct database info to connect

4. **Starting the app**
   Run these commands
   ```sh
   mvn clean install
   ```
   ```sh
   mvn clean javafx:run
   ```
   
## Technologies Used

  - Java
  - Spring Boot
  - Maven
  - JavaFX
  - MariaDB
  - JUnit
  - Jacoco
  - Jenkins

------------------------------------------------

![Copyright of Vectra Vault Inc](https://github.com/user-attachments/assets/7257e3f5-ee8f-4eab-a495-60a40e0de3c9)



