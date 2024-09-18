package com.reppuhallinta.inventory_management_sys.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.*;
import javafx.stage.Stage;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import session.CustomSessionManager;

@Controller
public class ProductViewController {

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
    }

    private void loadProductData() {
        List<Products> products = productService.getAllProducts();
        ObservableList<Products> productObservableList = FXCollections.observableArrayList(products);
        productTable.setItems(productObservableList);
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

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this product?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                int productId = selectedProduct.getId();
                transactionService.removeTransactionByProductId(productId);

                productService.deleteProduct(selectedProduct.getId());
                loadProductData();
            }
        }

    }

    public void handleRefreshButtonAction() {
        loadProductData();
    }

}