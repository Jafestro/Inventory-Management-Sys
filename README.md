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

![image2](https://github.com/user-attachments/assets/9fe66af1-6763-4b66-995f-974c2558c119)



![image](https://github.com/user-attachments/assets/3c305bc0-eafb-402d-9f24-f49847513607)

## Use Case Diagram
![image](https://github.com/user-attachments/assets/4cde667a-cfe7-4657-ae3e-1c7302979ebb)

## Activity Diagram (Example)
![image](https://github.com/user-attachments/assets/9678d025-95cb-45a6-915d-8502d89b6ba7)



