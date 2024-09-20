package com.reppuhallinta.inventory_management_sys.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.reppuhallinta.inventory_management_sys.utils.UIUtils;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.reppuhallinta.inventory_management_sys.model.Products;
import com.reppuhallinta.inventory_management_sys.model.User;
import com.reppuhallinta.inventory_management_sys.service.ProductService;
import com.reppuhallinta.inventory_management_sys.service.TransactionService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

        UIUtils.loadFXML("/Transactions.fxml", stage, "Transactions", 1200, 1200, null);

    }

    public void handleCreateProductButtonAction() {
        Stage stage = new Stage();

        UIUtils.loadFXML("/CreateProduct.fxml", stage, "Create Product", 400, 350, null);
    }

    @FXML
    public void handleDeleteProductButtonAction() {
        Products selectedProduct = productTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            UIUtils.showAlert(AlertType.WARNING, "Warning", null, "Please select a product to delete");
            return;
        }

        Optional<ButtonType> result = UIUtils.showConfirmationDialog("Confirmation", null, "Are you sure you want to delete this product?");

        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                int productId = selectedProduct.getId();
                transactionService.removeTransactionByProductId(productId);

                productService.deleteProduct(selectedProduct.getId());
                loadProductData();
            }
        }

    }

   @FXML
private void handleEditProduct() {
    Products selectedProduct = productTable.getSelectionModel().getSelectedItem();
    if (selectedProduct == null) {
        UIUtils.showAlert(AlertType.WARNING, "Warning", null, "Please select a product to edit");
        return;
    }

    try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EditProduct.fxml"));
        fxmlLoader.setControllerFactory(UIUtils.getSpringContext()::getBean);
        Parent root = fxmlLoader.load();

        EditProductController editProductController = fxmlLoader.getController();
        editProductController.setProduct(selectedProduct);

        Stage stage = new Stage();
        stage.setScene(new Scene(root, 400, 350));
        stage.setTitle("Edit Product");
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public void handleRefreshButtonAction() {
        loadProductData();
    }

}