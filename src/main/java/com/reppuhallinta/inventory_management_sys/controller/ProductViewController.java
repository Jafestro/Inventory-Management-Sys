package com.reppuhallinta.inventory_management_sys.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.reppuhallinta.inventory_management_sys.model.Products;
import com.reppuhallinta.inventory_management_sys.model.User;
import com.reppuhallinta.inventory_management_sys.service.ProductService;
import com.reppuhallinta.inventory_management_sys.service.TransactionService;
import com.reppuhallinta.inventory_management_sys.utils.FXMLLoaderUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import session.CustomSessionManager;

@Controller
public class ProductViewController {

    private static final int REORDER_THRESHOLD = 3; // Threshold value for reordering

    @Autowired
    private ProductService productService;

    @Autowired
    private TransactionService transactionService;

    @FXML
    private TableView<Products> productTable;

    @FXML
    private Button transactionButton;

    @FXML
    private TableColumn<Products, Integer> idColumn;

    @FXML
    private TableColumn<Products, String> nameColumn;

    @FXML
    private TableColumn<Products, Integer> quantityColumn;

    @FXML
    private TableColumn<Products, BigDecimal> priceColumn;

    @FXML
    private TableColumn<Products, Integer> supplierIDColumn;

    @FXML
    private TableColumn<Products, Integer> categoryIDColumn;

    @FXML
    private Button refreshButton;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
 
    @FXML
    public void initialize() {

        String sessionId = CustomSessionManager.getSessionId();
        System.out.println("Session ID in ProductViewController: " + sessionId);

        User user = (User) CustomSessionManager.getAttribute("user");

        if (user != null) {
            System.out.println("Logged in user: " + user.getUsername());
        } else {
            System.out.println("No user logged in");
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        supplierIDColumn.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
        categoryIDColumn.setCellValueFactory(new PropertyValueFactory<>("categoryId"));

        loadProductData();

        scheduler.scheduleAtFixedRate(this::checkStockLevels, 0, 1, TimeUnit.MINUTES);
    }

    private void loadProductData() {
        List<Products> products = productService.getAllProducts();
        ObservableList<Products> productObservableList = FXCollections.observableArrayList(products);
        productTable.setItems(productObservableList);
    }

    private void checkStockLevels() {
        List<Products> products = productService.getAllProducts();
        for (Products product : products) {
            if (product.getQuantity() < REORDER_THRESHOLD) {
                triggerReorder(product);
            }
        }
    }

    private void triggerReorder(Products product) {
        // Implement the reorder logic here
        // For example, create a new order or notify the supplier
        System.out.println("Reordering product: " + product.getProductName());
        // You can also show an alert to the user
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Reorder Notification");
        alert.setHeaderText(null);
        alert.setContentText("Product " + product.getProductName() + " is below the threshold. Reordering now.");
        alert.showAndWait();
    }

    public void handleTransactionButtonAction() {

        Stage stage = (Stage) transactionButton.getScene().getWindow();

        FXMLLoaderUtil.loadFXML("/Transactions.fxml", stage, "Transactions", 1200, 1200);

    }

    public void handleCreateProductButtonAction() {
        Stage stage = new Stage();

        FXMLLoaderUtil.loadFXML("/CreateProduct.fxml", stage, "Create Product", 400, 350);
    }

    @FXML
    public void handleDeleteProductButtonAction() {
        Products selectedProduct = productTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select a product to delete.");
            alert.showAndWait();

            return;
        }

        int productId = selectedProduct.getId();
        transactionService.removeTransactionByProductId(productId);

        productService.deleteProduct(selectedProduct.getId());
        loadProductData();
    }

    public void handleRefreshButtonAction() {
        loadProductData();
    }
}