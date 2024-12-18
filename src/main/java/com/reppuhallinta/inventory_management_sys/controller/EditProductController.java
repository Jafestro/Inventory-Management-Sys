package com.reppuhallinta.inventory_management_sys.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Controller;

import com.reppuhallinta.inventory_management_sys.model.Category;
import com.reppuhallinta.inventory_management_sys.model.Products;
import com.reppuhallinta.inventory_management_sys.model.Suppliers;
import com.reppuhallinta.inventory_management_sys.model.User;
import com.reppuhallinta.inventory_management_sys.service.CategoryService;
import com.reppuhallinta.inventory_management_sys.service.ProductService;
import com.reppuhallinta.inventory_management_sys.service.SupplierService;
import com.reppuhallinta.inventory_management_sys.utils.UIUtils;
import static com.reppuhallinta.inventory_management_sys.utils.UIUtils.ERRORTITLE;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import session.CustomSessionManager;

/**
 * Controller class for editing products.
 */
@Controller
public class EditProductController {

    private final ProductService productService;

    private final CategoryService categoryService;

    private final SupplierService supplierService;

    @FXML
    public TextField productNameField;

    @FXML
    public TextField productPriceField;

    @FXML
    public TextField quantityField;

    @FXML
    public ComboBox<String> categoryComboBox;

    @FXML
    public ComboBox<String> supplierComboBox;

    @FXML
    private Button editButton;

    private User user;

    private Products product;

    private List<Category> categories;

    private List<Suppliers> suppliers;

    /**
     * Constructor for EditProductController.
     * 
     * @param productService the service for managing products
     * @param categoryService the service for managing categories
     * @param supplierService the service for managing suppliers
     */
    public EditProductController(ProductService productService, CategoryService categoryService, SupplierService supplierService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.supplierService = supplierService;
    }

    /**
     * Sets the product to be edited and pre-fills the data.
     * 
     * @param product the product to set
     */
    public void setProduct(Products product) {
        this.product = product;
        if (categories != null && suppliers != null) {
            prefillFields();
        }
    }

    /**
     * Initializes the controller class.
     */
    @FXML
    public void initialize() {
        categories = getAllCategories();
        suppliers = getAllSuppliers();

        for (Category category : categories) {
            categoryComboBox.getItems().add(category.getCategoryName());
        }

        for (Suppliers supplier : suppliers) {
            supplierComboBox.getItems().add(supplier.getSupplierName());
        }

        if (product != null) {
            prefillFields();
        }

        user = (User) CustomSessionManager.getAttribute("user");
    }

    /**
     * Pre-fills the fields with the product data.
     */
    private void prefillFields() {
        productNameField.setText(product.getProductName());
        productPriceField.setText(product.getPrice().toString());
        quantityField.setText(String.valueOf(product.getQuantity()));

        for (Category category : categories) {
            if (category.getId() == product.getCategoryId()) {
                categoryComboBox.setValue(category.getCategoryName());
            }
        }

        for (Suppliers supplier : suppliers) {
            if (supplier.getSupplierID() == product.getSupplierID()) {
                supplierComboBox.setValue(supplier.getSupplierName());
            }
        }
    }


    /**
     * Handles the action when the update product button is clicked.
     */
    public void updateProductButtonOnAction() {
        String productName = productNameField.getText();
        String productPrice = productPriceField.getText();
        String quantity = quantityField.getText();
        String category = categoryComboBox.getValue();
        String supplier = supplierComboBox.getValue();

        if (productName.isEmpty() || productPrice.isEmpty() || quantity.isEmpty() || category == null || supplier == null) {
            return;
        }

        BigDecimal price;

        try {
            price = new BigDecimal(productPrice);
            if (price.compareTo(BigDecimal.ZERO) <= 0) {
                UIUtils.showAlert(Alert.AlertType.ERROR, ERRORTITLE, null, "error.productPriceMustBePositive");
                return;
            }
        } catch (NumberFormatException e) {
            UIUtils.showAlert(Alert.AlertType.ERROR, ERRORTITLE, null, "error.productPriceMustBeNumeric");
            return;
        }

        int quantityInt;

        try {
            quantityInt = Integer.parseInt(quantity);
            if (quantityInt < 0) {
                UIUtils.showAlert(Alert.AlertType.ERROR, ERRORTITLE, null, "error.productQuantityCannotBeNegative");
                return;
            }
        } catch (NumberFormatException e) {
            UIUtils.showAlert(Alert.AlertType.ERROR, ERRORTITLE, null, "error.productQuantityMustBeNumeric");
            return;
        }

        Products updatedProduct = new Products();
        updatedProduct.setProductName(productName);
        updatedProduct.setPrice(BigDecimal.valueOf(Double.parseDouble(productPrice)));
        updatedProduct.setQuantity(Integer.parseInt(quantity));

        for (Category c : categories) {
            if (c.getCategoryName().equals(category)) {
                updatedProduct.setCategoryId(c.getId());
            }
        }

        for (Suppliers s : suppliers) {
            if (s.getSupplierName().equals(supplier)) {
                updatedProduct.setSupplierID(s.getSupplierID());
            }
        }

        if (user != null) {
            productService.updateProduct(product.getId(), updatedProduct, user.getId());

            UIUtils.showConfirmationDialog("Product Edit Successful", null, "Product has been edited!");
            Stage currentStage = (Stage) editButton.getScene().getWindow();
            currentStage.close();
        }
    }

     /**
     * Retrieves all categories.
     * 
     * @return a list of all categories
     */
    private List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    /**
     * Retrieves all suppliers.
     * 
     * @return a list of all suppliers
     */
    private List<Suppliers> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }
}