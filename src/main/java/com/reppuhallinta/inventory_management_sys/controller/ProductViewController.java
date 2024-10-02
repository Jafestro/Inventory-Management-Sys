package com.reppuhallinta.inventory_management_sys.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.reppuhallinta.inventory_management_sys.utils.UIUtils;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.reppuhallinta.inventory_management_sys.model.Products;
import com.reppuhallinta.inventory_management_sys.model.User;
import com.reppuhallinta.inventory_management_sys.service.ProductService;
import com.reppuhallinta.inventory_management_sys.service.TransactionService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import session.CustomSessionManager;

@Controller
public class ProductViewController extends LogoutController {

    private static final int REORDER_THRESHOLD = 3; // Threshold value for reordering

    @Autowired
    private ProductService productService;

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
    private TextField searchField;

    @FXML
    private Button reportsButton;

    @FXML
    private Button createProductButton;

    @FXML
    private Button editProductButton;

    @FXML
    private Button deleteProductButton;

    @FXML
    private ProgressBar autoRefreshProgressBar;


    @FXML
    public void initialize() {

        String sessionId = CustomSessionManager.getSessionId();
        System.out.println("Session ID in ProductViewController: " + sessionId);

        User user = (User) CustomSessionManager.getAttribute("user");

        if (user != null) {
            if (!"admin".equals(user.getAccessLevel())) {
                createProductButton.setDisable(true);
                editProductButton.setDisable(true);
                deleteProductButton.setDisable(true);
                
                Tooltip tooltip = new Tooltip("You do not have permission to perform this action");
                createProductButton.setTooltip(tooltip);
                editProductButton.setTooltip(tooltip);
                deleteProductButton.setTooltip(tooltip);
            }
        } 

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterProductList(newValue);
        });

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        supplierIDColumn.setCellValueFactory(new PropertyValueFactory<>("supplierID"));
        categoryIDColumn.setCellValueFactory(new PropertyValueFactory<>("categoryId"));
      
        addSortEventHandler(idColumn);
        addSortEventHandler(nameColumn);
        addSortEventHandler(quantityColumn);
        addSortEventHandler(priceColumn);
        addSortEventHandler(supplierIDColumn);
        addSortEventHandler(categoryIDColumn);

        loadProductData();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), event -> {
                    loadProductData();
                    updateProgressBar();
                }),
                new KeyFrame(Duration.seconds(15))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        scheduler.scheduleAtFixedRate(this::checkStockLevels, 0, 1, TimeUnit.MINUTES);
    }
  
    private <T> void addSortEventHandler(TableColumn<Products, T> column) {
       Label label = new Label();
       column.setGraphic(label);
       label.setOnMouseClicked(event -> {
            if (column.getSortType() == TableColumn.SortType.ASCENDING) {
               column.setSortType(TableColumn.SortType.DESCENDING);
            } else {
                column.setSortType(TableColumn.SortType.ASCENDING);
            }
            productTable.getSortOrder().clear();
            productTable.getSortOrder().add(column);
        });
    }

    private void filterProductList(String searchWord) {
        ObservableList<Products> filteredList = FXCollections.observableArrayList();

        for (Products product : productService.getAllProducts()) {
            if (product.getProductName().toLowerCase().contains(searchWord.toLowerCase())) {
                filteredList.add(product);
            }
        }
    
        productTable.setItems(filteredList);
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

        UIUtils.loadFXML("/Transactions.fxml", stage, "Transactions", 1300, 600, null);

    }

    public void handleCreateProductButtonAction() {
        Stage stage = new Stage();

        UIUtils.loadFXML("/CreateProduct.fxml", stage, "Create Product", 400, 500, null);
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

    public void handleReportsButtonAction() {
        Stage stage = new Stage();

        UIUtils.loadFXML("/Reports.fxml", stage, "Reports", 424, 303, null);
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

        productService.deleteProduct(selectedProduct.getId());
        loadProductData();
    }

    public void handleRefreshButtonAction() {
        loadProductData();
    }

    private void updateProgressBar() {
        Timeline progressBarTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(autoRefreshProgressBar.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(15), new KeyValue(autoRefreshProgressBar.progressProperty(), 1))
        );
        progressBarTimeline.setCycleCount(1);
        progressBarTimeline.play();
    }
}