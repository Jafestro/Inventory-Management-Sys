package com.reppuhallinta.inventory_management_sys.controller;

import com.reppuhallinta.inventory_management_sys.model.Category;
import com.reppuhallinta.inventory_management_sys.model.Products;
import com.reppuhallinta.inventory_management_sys.model.Suppliers;
import com.reppuhallinta.inventory_management_sys.model.User;
import com.reppuhallinta.inventory_management_sys.service.CategoryService;
import com.reppuhallinta.inventory_management_sys.service.ProductService;
import com.reppuhallinta.inventory_management_sys.service.SupplierService;

import com.reppuhallinta.inventory_management_sys.utils.UIUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import session.CustomSessionManager;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class EditProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SupplierService supplierService;

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

    public void setProduct(Products product) {
        this.product = product;
        if (categories != null && suppliers != null) {
            prefillFields();
        }
    }

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

        String sessionId = CustomSessionManager.getSessionId();
        System.out.println("Session ID in ProductViewController: " + sessionId);

        user = (User) CustomSessionManager.getAttribute("user");
    }

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
                UIUtils.showAlert(Alert.AlertType.ERROR, "alert.error", null, "error.productPriceMustBePositive");
                return;
            }
        } catch (NumberFormatException e) {
            UIUtils.showAlert(Alert.AlertType.ERROR, "alert.error", null, "error.productPriceMustBeNumeric");
            return;
        }

        int quantityInt;

        try {
            quantityInt = Integer.parseInt(quantity);
            if (quantityInt < 0) {
                UIUtils.showAlert(Alert.AlertType.ERROR, "alert.error", null, "error.productQuantityCannotBeNegative");
                return;
            }
        } catch (NumberFormatException e) {
            UIUtils.showAlert(Alert.AlertType.ERROR, "alert.error", null, "error.productQuantityMustBeNumeric");
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

    private List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    private List<Suppliers> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }
}