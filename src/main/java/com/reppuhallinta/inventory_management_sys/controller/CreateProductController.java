package com.reppuhallinta.inventory_management_sys.controller;

import com.reppuhallinta.inventory_management_sys.model.*;
import com.reppuhallinta.inventory_management_sys.service.CategoryService;
import com.reppuhallinta.inventory_management_sys.service.SupplierService;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.reppuhallinta.inventory_management_sys.service.ProductService;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import session.CustomSessionManager;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class CreateProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SupplierService supplierService;

    @FXML
    private TextField productNameField;

    @FXML
    private TextField productPriceField;

    @FXML
    private TextField quantityField;

    @FXML
    private ComboBox categoryComboBox;

    @FXML
    private ComboBox supplierComboBox;

    @FXML
    private Button createButton;

    List<Category> categories;

    List<Suppliers> suppliers;

    @FXML
    public void initialize() {
        categories = getAllTransactions();
        suppliers = getAllSuppliers();

        for (Category category : categories) {
            categoryComboBox.getItems().add(category.getCategoryName());
        }

        for (Suppliers supplier : suppliers) {
            supplierComboBox.getItems().add(supplier.getSupplierName());
        }
    }

    @FXML
    private void handleCreateProduct() {
        String productName = this.productNameField.getText();
        String productPrice = this.productPriceField.getText();
        String quantity = this.quantityField.getText();
        int categoryId = 0;
        int supplierId = 0;


        String categoryName = categoryComboBox.getValue().toString();
        String supplierName = supplierComboBox.getValue().toString();

        for (Category category : categories) {
            if (category.getCategoryName().equals(categoryName)) {
                categoryId = category.getId();
            }
        }

        for (Suppliers supplier : suppliers) {
            if (supplier.getSupplierName().equals(supplierName)) {
                supplierId = supplier.getSupplierID();
            }
        }


        String sessionId = CustomSessionManager.getSessionId();
        System.out.println("Session ID in CreateProductController: " + sessionId);

        User user = (User) CustomSessionManager.getAttribute("user");

        if (user != null) {
            System.out.println("Logged in user: " + user.getUsername());
        } else {
            System.out.println("No user logged in");
        }


        if (productName.isEmpty() || productPrice.isEmpty() || quantity.isEmpty() || categoryComboBox.getValue() == null || supplierComboBox.getValue() == null ) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter all the fields correctly.");
            return;
        }

        try {
            BigDecimal productPriceBigDecimal = new BigDecimal(productPrice);
            int quantityInt = Integer.parseInt(quantity);

            Products product = new Products();
            product.setProductName(productName);
            product.setPrice(productPriceBigDecimal);
            product.setQuantity(quantityInt);
            product.setCategoryId(categoryId);
            product.setSupplierID(supplierId);

            System.out.println("Product that's being created: " + product);

            assert user != null;
            productService.createProduct(product, user.getId());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please enter all the fields correctly.");
        }
    }


    private List<Category> getAllTransactions() {
        return categoryService.getAllCategories();
    }

    private List<Suppliers> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
