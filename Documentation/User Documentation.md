## Installation
- Clone the repository
- Run `mvn clean install` in the root directory of the project
- Run `mvn clean javafx:run` to start the application
- If you are using Intellij IDEA, you might need to add JavaFX to the Library. You can do this by following the steps below:
    - Go to File -> Project Structure
    - Click on Libraries
    - Click on the '+' icon and select Java
    - Navigate to the JavaFX lib folder and select the lib folder
    - Click on Apply and OK

### Prerequisites

- [Maven](https://maven.apache.org/download.cgi)
- [Java SDK](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [JavaFX](https://openjfx.io/)

### Usage
- Login: On the login screen, enter the username and password and click the login button to access the application.
- Register: On the login screen, click on the 'Are you not registered yet' link to create a new account.
- Product Creation: Click on the 'Create Product' button to create a new product.
- Edit Product: Select a product from the table and click on the 'Edit Product' button to edit the product details.
- Delete Product: Select a product from the table and click on the 'Delete Product' button to delete the product.
- View Transaction: Click on the 'Transactions' button to view all the transactions.
- Edit Transaction: Select a transaction from the table and click on the 'Edit Transaction' button to edit the transaction details.
- Export Report: In Products page click on the 'Reports' button and select if you would like to export all, by username, by product id or by transaction date.
- Logout: Click on the 'Logout' button to logout from the application.