package com.reppuhallinta.inventory_management_sys.controller;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.reppuhallinta.inventory_management_sys.model.Products;
import com.reppuhallinta.inventory_management_sys.model.User;
import com.reppuhallinta.inventory_management_sys.service.ProductService;
import com.reppuhallinta.inventory_management_sys.utils.UIUtils;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import session.CustomSessionManager;

/**
 * Controller class for managing product views.
 */
@Controller
public class ProductViewController extends LogoutController {

    private static final int REORDER_THRESHOLD = 3; // Threshold value for reordering

    private final ProductService productService;

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
    private ChoiceBox<String> languageChoiceBox;

    private static final Logger logger = LoggerFactory.getLogger(ProductViewController.class);

    private Timeline timeline;

    /**
     * Constructor for ProductViewController.
     * 
     * @param productService the service for managing products
     */
    public ProductViewController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {

        User user = (User) CustomSessionManager.getAttribute("user");

        if (user != null && !"admin".equals(user.getAccessLevel())) {
            createProductButton.setDisable(true);
            editProductButton.setDisable(true);
            deleteProductButton.setDisable(true);
        }



        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterProductList(newValue));

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        supplierIDColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        categoryIDColumn.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
      
        addSortEventHandler(idColumn);
        addSortEventHandler(nameColumn);
        addSortEventHandler(quantityColumn);
        addSortEventHandler(priceColumn);
        addSortEventHandler(supplierIDColumn);
        addSortEventHandler(categoryIDColumn);

        loadProductData();

        System.out.println(timeline);

        if (timeline != null) {
            timeline.stop();
            timeline.setCycleCount(0);
        }

         timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), event -> {
                    loadProductData();
                    updateProgressBar();
                }),
                new KeyFrame(Duration.seconds(15))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        scheduler.scheduleAtFixedRate(this::checkStockLevels, 0, 1, TimeUnit.MINUTES);

        UIUtils.setLanguageChoiceBox(languageChoiceBox, "Products", "/Products.fxml", 1050, 600);
    }
  
    /**
     * Adds a sort event handler to the given table column.
     * 
     * @param column the table column to add the sort event handler to
     * @param <T> the type of the column
     */
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

    /**
     * Filters the product list based on the search word.
     * 
     * @param searchWord the search word to filter the product list
     */
    private void filterProductList(String searchWord) {
        ObservableList<Products> filteredList = FXCollections.observableArrayList();

        for (Products product : productService.getAllProducts()) {
            if (product.getProductName().toLowerCase().contains(searchWord.toLowerCase())) {
                filteredList.add(product);
            }
        }
    
        productTable.setItems(filteredList);
    }

    /**
     * Loads the product data and sets it to the table.
     */
    private void loadProductData() {
        List<Products> products = productService.getAllProducts();
        ObservableList<Products> productObservableList = FXCollections.observableArrayList(products);
        productTable.setItems(productObservableList);
    }

    /**
     * Checks the stock levels of products and triggers reorder if necessary.
     */
    private void checkStockLevels() {
        List<Products> products = productService.getAllProducts();
        for (Products product : products) {
            if (product.getQuantity() < REORDER_THRESHOLD) {
                triggerReorder(product);
            }
        }
    }

    /**
     * Triggers reorder for the given product.
     * 
     * @param product the product to reorder
     */
    private void triggerReorder(Products product) {
        // Implement the reorder logic here
        // For example, create a new order or notify the supplier
        logger.info("Reordering product: {}", product.getProductName());
        // You can also show an alert to the user
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Reorder Notification");
        alert.setHeaderText(null);
        alert.setContentText("Product " + product.getProductName() + " is below the threshold. Reordering now.");
        alert.showAndWait();
    }

    /**
     * Handles the action when the transaction button is clicked.
     */
    public void handleTransactionButtonAction() {

        Stage stage = (Stage) transactionButton.getScene().getWindow();

        UIUtils.loadFXML("/Transactions.fxml", stage, "Transactions", 1050, 600, null);

    }

    /**
     * Handles the action when the create product button is clicked.
     */
    public void handleCreateProductButtonAction() {
        Stage stage = new Stage();

        UIUtils.loadFXML("/CreateProduct.fxml", stage, "Create Product", 500, 600, null);
    }

    /**
     * Handles the action when the edit product button is clicked.
     */
    @FXML
    private void handleEditProduct() {
        Products selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            UIUtils.showAlert(AlertType.WARNING, "alert.warning", null, "warning.selectProducttoEdit");
            return;
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/EditProduct.fxml"));
            fxmlLoader.setControllerFactory(UIUtils.getSpringContext()::getBean);
            String localeString = UIUtils.getLocale();
            fxmlLoader.setResources(ResourceBundle.getBundle("bundle_" + localeString, Locale.forLanguageTag(localeString)));
            Parent root = fxmlLoader.load();

            EditProductController editProductController = fxmlLoader.getController();
            editProductController.setProduct(selectedProduct);

            Stage stage = new Stage();
            stage.setScene(new Scene(root, 400, 450));
            stage.setTitle("Edit Product");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the action when the reports button is clicked.
     */
    public void handleReportsButtonAction() {
        Stage stage = new Stage();

        UIUtils.loadFXML("/Reports.fxml", stage, "Reports", 524, 600, null);
    }

    /**
     * Handles the action when the delete product button is clicked.
     */
    @FXML
    public void handleDeleteProductButtonAction() {
        Products selectedProduct = productTable.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            UIUtils.showAlert(AlertType.WARNING, "alert.warning", null, "warning.selectProductToDelete");
            return;
        }

        productService.deleteProduct(selectedProduct.getId());
        loadProductData();
    }

    /**
     * Handles the action when the refresh button is clicked.
     */
    public void handleRefreshButtonAction() {
        loadProductData();
    }

    /**
     * Updates the progress bar.
     */
    private void updateProgressBar() {
        Timeline progressBarTimeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(autoRefreshProgressBar.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(15), new KeyValue(autoRefreshProgressBar.progressProperty(), 1))
        );
        progressBarTimeline.setCycleCount(1);
        progressBarTimeline.play();
    }
}