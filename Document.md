# **Inventory Management System Documentation**

---

## **Table of Contents**

- [Introduction](#introduction)
- [Main Features](#main-features)
- [System Requirements](#system-requirements)
- [Getting Started](#getting-started)
    - [Clone the Repository](#clone-the-repository)
    - [Database Setup](#database-setup)
    - [Run the Application](#run-the-application)
- [Technologies Used](#technologies-used)
- [Diagrams](#diagrams)
    - [Use Case Diagram](#use-case-diagram)
    - [Activity Diagram](#activity-diagram)
- [Contribution Guidelines](#contribution-guidelines)
- [FAQs](#faqs)
- [Future Enhancements](#future-enhancements)
- [License](#license)

---

## **Introduction**

**The Inventory Management System (IMS) is a robust and intuitive tool tailored to streamline inventory operations for businesses of all sizes. Designed to handle tasks like product management, transaction tracking, and reporting, IMS ensures accuracy, scalability, and efficiency. Whether you’re managing a small business or a large enterprise, IMS provides a secure and customizable solution to meet diverse operational needs.**

---

## **Main Features**

- **Product Management**: Add, delete, and edit products in your inventory.
- **Transaction Tracking**: Transaction Tracking: Monitor product transactions to track changes made by users. For example, view who updated stock levels and when, helping ensure accountability and transparency.
- **Automated Reordering**: Enable automated reorders when stock levels fall below a threshold.
- **Reports**: Export stock change reports to your computer for analysis.
- **User Roles**: Assign user roles as either Admin (full permissions) or Regular User (limited permissions).

---

## **System Requirements**

- **Operating System**: Windows 10+, macOS, Linux
- **Java Version**: Java 11 or higher
- **Database**: MariaDB 10.5 or later
- **Memory (RAM)**: Minimum 2 GB
- **Storage**: Minimum 500 MB of free space
- **Build Tools**: Maven 3.6+

---

# **Getting Started**

## **Prerequisites**
- **Install java 11 or higher.**
- **Install Maven(3.6+)**
- **Ensure MariaDB is installed and running.**

### **1. Clone the Repository**

Use the following command to clone the repository:

```bash
git clone https://github.com/Jafestro/Inventory-Management-Sys.git
```
### **2. Navigate to the project directory.**
````bash
cd Inventory-Management-Sys
````
# **Database setup** ##
### 1. Create the database:
```bash
CREATE DATABASE {yourdatabasename};
USE {yourdatabasename};
```
###  2. Run the SQL script to set up tables and columns:
```bash
# Example command
mysql -u <username> -p < yourscript.sql
```

# **Run the Application**

 **1. Build the Project**:
   Use the following command to build the application:
   ```bash
   mvn clean install
   ```

**2. Run the Application: Use this command to start the application:**
```bash
mvn clean javafx:run
```
**Note: Ensure all dependencies are installed, and the database is correctly configured before running the application.**

---
# **Troubleshooting** 
- **If the database connection fails, verify the application.properties file contains correct credentials.**
- **Ensure that you are using the VPN**
- **Ensure  MariaDB is running:**
```bash
sudo systemctl start mariadb

```
---
## **FAQs**

1. **What should I do if the database fails to connect?**
  - Check the following:
    - Correct database credentials in `application.properties`.
    - The MariaDB server is running.
    - Network configuration allows database access.

2. **How can I reset admin privileges if locked out?**
  - Log into the database directly and update the admin user’s password:
    ```sql
    UPDATE users SET password = '<new_password>' WHERE username = 'admin';
    ```

3. **Is the system scalable for larger inventories?**
  - Yes, IMS is built using scalable technologies like Spring Boot and MariaDB, allowing for handling of large datasets.


---

## **Contribution Guidelines**

We welcome contributions to the IMS project! Here’s how you can get involved:

1. **Fork the Repository**:
  - Create a personal copy of the repository.
  - Clone it to your local machine:
    ```bash
    git clone https://github.com/<your-username>/Inventory-Management-Sys.git
    ```
  - Create a new branch for your changes:
    ```bash
    git checkout -b feature/<your-feature-name>
    ```

2. **Make Your Changes**:
  - Follow the coding standards outlined in our `CONTRIBUTING.md`.
  - Run tests to ensure code quality:
    ```bash
    mvn test
    ```

3. **Submit a Pull Request**:
  - Push your branch:
    ```bash
    git push origin feature/<your-feature-name>
    ```
  - Open a pull request in the original repository, describing your changes.

4. **Code Review**:
  - Be prepared to receive feedback during the review process.
  - Address requested changes promptly to get your feature merged.

---
## **Release Notes**

### **v1.0.0**
- Initial release of Inventory Management System.
- Features:
  - Product management (add, delete, edit)
  - Transaction tracking
  - Automated reordering
  - Basic reporting
  - User roles (Admin, Regular)

---

### **Use Case Diagram**
![Use Case Diagram](https://github.com/user-attachments/assets/4cde667a-cfe7-4657-ae3e-1c7302979ebb)
_Figure 1: Overview of Use Cases in IMS_
---
### **Activity Diagram**
![Activity Diagram](https://github.com/user-attachments/assets/9678d025-95cb-45a6-915d-8502d89b6ba7)
_Figure 2: Example of an Activity Flow in IMS_

## **License**

This project is licensed under the **MIT License**. You are free to use, modify, and distribute this software. See the `LICENSE` file for full details.
