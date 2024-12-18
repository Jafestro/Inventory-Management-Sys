package com.reppuhallinta.inventory_management_sys.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.reppuhallinta.inventory_management_sys.model.Products;
import com.reppuhallinta.inventory_management_sys.model.Transaction;
import com.reppuhallinta.inventory_management_sys.model.User;
import com.reppuhallinta.inventory_management_sys.service.ProductService;
import com.reppuhallinta.inventory_management_sys.service.TransactionService;
import com.reppuhallinta.inventory_management_sys.service.UserService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Controller class for managing report views.
 */
@Controller
public class ReportViewController {

    @FXML private Button getAllStockProductReportButton;
    @FXML private Button transByIdButton;
    @FXML private TextField transByIdField;
    @FXML private Button transByUserButton;
    @FXML private TextField transByUserField;
    @FXML private DatePicker transByDateDate;
    @FXML private Button transByDateButton;
    @FXML private ChoiceBox<String> transByUserChoiceBox;

    private final ProductService productService;

    private final TransactionService transactionService;

    private final UserService userService;

    @FXML private List<User> users;

    LocalDateTime notFormattedTime = LocalDateTime.now();
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    LocalDate date = LocalDate.now();
    String time = notFormattedTime.format(myFormatObj);

    /**
     * Constructor for ReportViewController.
     * 
     * @param productService the service for managing products
     * @param transactionService the service for managing transactions
     * @param userService the service for managing users
     */
    public ReportViewController(ProductService productService, TransactionService transactionService, UserService userService) {
        this.productService = productService;
        this.transactionService = transactionService;
        this.userService = userService;
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    private void initialize() {
        users = userService.getAllUsers();

        for (User user : users) {
            transByUserChoiceBox.getItems().add(user.getUsername());
        }

    }

    /**
     * Handles the action for generating a report of all stock products.
     */
    @FXML
    private void getAllStockProductReportButtonAction() {
        List<Products> products = productService.getAllProducts();
        StringBuilder report = new StringBuilder("All stock products " + time + "\n\n");
        report.append(String.format("%-10s %-20s %-10s %-10s %-10s %-10s%n", "ID", "Name", "Price", "Quantity", "Category", "Supplier"));
        report.append("--------------------------------------------------------------------------\n");
        for (Products product : products) {
            report.append(String.format("%-10d %-20s %-10.2f %-10d %-10d %-10d%n",
                    product.getId(),
                    product.getProductName(),
                    product.getPrice(),
                    product.getQuantity(),
                    product.getCategoryId(),
                    product.getSupplierID()));
            report.append("--------------------------------------------------------------------------\n");
        }
        String filename = "AllStockProducts" + date;
        generateDownloadableFile(report.toString(), filename);
    }

    /**
     * Generates a downloadable file with the given content and filename.
     * 
     * @param content the content to write to the file
     * @param filename the name of the file
     */
    private void generateDownloadableFile(String content, String filename) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Report");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files","*.txt"));
        fileChooser.setInitialFileName(filename + ".txt");
        Stage stage = (Stage) getAllStockProductReportButton.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Handles the action for generating a report of transactions by product ID.
     */
    @FXML
    private void getTransActionsByProductId() {
        int id = Integer.parseInt(transByIdField.getText());
        List<Transaction> transactions = transactionService.getTransactionsByProductId(id);
        StringBuilder report = new StringBuilder("Transactions by product id " + id + " " + time + "\n\n");
        report.append(String.format("%-10s %-10s %-10s %-20s %-10s %-10s%n", "ID", "Product", "Quantity", "Date", "Type", "User"));
        report.append("--------------------------------------------------------------------------\n");
        for (Transaction transaction : transactions) {
            report.append(String.format("%-10d %-10d %-10d %-20s %-10s %-10d%n",
                    transaction.getTransactionId(),
                    transaction.getProductId(),
                    transaction.getQuantity(),
                    transaction.getTransactionDate(),
                    transaction.getTransactionType(),
                    transaction.getUserId()));
            report.append("--------------------------------------------------------------------------\n");
        }
        String filename = "Product_" + id + "_transactions_" + date;
        generateDownloadableFile(report.toString(), filename);
    }

    /**
     * Handles the action for generating a report of transactions by user ID.
     */
    @FXML
    private void getTransActionsByUserId() {

        String selectedUsername = transByUserChoiceBox.getSelectionModel().getSelectedItem();

        User selectedUser = users.stream()
                .filter(user -> user.getUsername().equals(selectedUsername))
                .findFirst()
                .orElse(null);

        if (selectedUser == null) {
            return;
        }

        int id = selectedUser.getId();

        List<Transaction> transactions = transactionService.getTransactionsByUserId(id);
        StringBuilder report = new StringBuilder("Transactions by user " + selectedUsername + " " + time + "\n\n");
        report.append(String.format("%-10s %-10s %-10s %-20s %-10s %-10s%n", "ID", "Product", "Quantity", "Date", "Type", "User"));
        report.append("--------------------------------------------------------------------------\n");
        for (Transaction transaction : transactions) {
            report.append(String.format("%-10d %-10d %-10d %-20s %-10s %-10d%n",
                    transaction.getTransactionId(),
                    transaction.getProductId(),
                    transaction.getQuantity(),
                    transaction.getTransactionDate(),
                    transaction.getTransactionType(),
                    transaction.getUserId()));
            report.append("--------------------------------------------------------------------------\n");
        }
        String filename = "TransactionsByUser_" + id + "_" + date;
        generateDownloadableFile(report.toString(), filename);
    }

    /**
     * Handles the action for generating a report of transactions by date.
     */
    @FXML
    private void getTransActionsByDate() {
        LocalDate localDate = transByDateDate.getValue();
        Date startDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(localDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<Transaction> transactions = transactionService.getTransactionsByDateRange(startDate, endDate);
        StringBuilder report = new StringBuilder("Transactions by date " + date + " " + time + "\n\n");
        report.append(String.format("%-10s %-10s %-10s %-20s %-10s %-10s%n", "ID", "Product", "Quantity", "Date", "Type", "User"));
        report.append("--------------------------------------------------------------------------\n");
        for (Transaction transaction : transactions) {
            report.append(String.format("%-10d %-10d %-10d %-20s %-10s %-10d%n",
                    transaction.getTransactionId(),
                    transaction.getProductId(),
                    transaction.getQuantity(),
                    transaction.getTransactionDate(),
                    transaction.getTransactionType(),
                    transaction.getUserId()));
            report.append("--------------------------------------------------------------------------\n");
        }
        String filename = "TransactionsByDate_" + date;
        generateDownloadableFile(report.toString(), filename);
    }
}