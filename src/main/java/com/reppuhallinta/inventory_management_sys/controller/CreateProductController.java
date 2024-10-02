package com.reppuhallinta.inventory_management_sys.controller;

import com.reppuhallinta.inventory_management_sys.model.*;
import com.reppuhallinta.inventory_management_sys.service.CategoryService;
import com.reppuhallinta.inventory_management_sys.service.SupplierService;
import com.reppuhallinta.inventory_management_sys.service.TransactionService;
import com.reppuhallinta.inventory_management_sys.utils.UIUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.reppuhallinta.inventory_management_sys.service.ProductService;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import session.CustomSessionManager;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Controller
public class CreateProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private TransactionService transactionService;

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
    private ComboBox<String> categoryComboBox;

    @FXML
    private ComboBox<String> supplierComboBox;

    @FXML
    private Button createButton;

    List<Category> categories;

    List<Suppliers> suppliers;

    @FXML
    private Button addNewCategoryButton;

    @FXML
    private Button AddNewSupplierButton;

    @FXML
    private TextField newSupplierTextField;

    @FXML
    private TextField newCategoryTextField;

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


        User user = (User) CustomSessionManager.getAttribute("user");


        if (productName.isEmpty() || productPrice.isEmpty() || quantity.isEmpty() || categoryComboBox.getValue() == null || supplierComboBox.getValue() == null ) {
            UIUtils.showAlert(Alert.AlertType.ERROR, "Error", null, "Please enter all the fields correctly.");
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

            if (user != null) {
                productService.createProduct(product, user.getId());

                success();
            } else {
                UIUtils.showAlert(Alert.AlertType.ERROR, "Error", null, "Please log in to create a product.");
                return;
            }
            

        } catch (Exception e) {
            UIUtils.showAlert(Alert.AlertType.ERROR, "Error", null, "Please enter all the fields correctly.");
        }
    }


    private List<Category> getAllTransactions() {
        return categoryService.getAllCategories();
    }

    private List<Suppliers> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    private void success() {
        UIUtils.showAlert(AlertType.INFORMATION, "Success", null, "Product created!");

        Stage currentStage = (Stage) createButton.getScene().getWindow();
        currentStage.close();

    }

    @FXML
    private void handleAddNewCategory() {
    	String newCategory = newCategoryTextField.getText();

    	if (newCategory.isEmpty()) {
            UIUtils.showAlert(Alert.AlertType.ERROR, "Error", null, "Please enter a category name.");
            return;
        }

    	Category category = new Category();
    	category.setCategoryName(newCategory);

    	categoryService.createCategory(category);

    	categoryComboBox.getItems().clear();

        categories = getAllTransactions();

        for (Category c : categories) {
            categoryComboBox.getItems().add(c.getCategoryName());
        }

    	newCategoryTextField.clear();
        UIUtils.showAlert(AlertType.INFORMATION, "Success", null, "Category added!");
    }

    @FXML
    private void handleAddNewSupplier() {
    	String newSupplier = newSupplierTextField.getText();

    	if (newSupplier.isEmpty()) {
            UIUtils.showAlert(Alert.AlertType.ERROR, "Error", null, "Please enter a supplier name.");
            return;
        }

    	Suppliers supplier = new Suppliers();
    	supplier.setSupplierName(newSupplier);

    	supplierService.createSupplier(supplier);

    	supplierComboBox.getItems().clear();

        suppliers = getAllSuppliers();

        for (Suppliers s : suppliers) {
            supplierComboBox.getItems().add(s.getSupplierName());
        }

    	newSupplierTextField.clear();
        UIUtils.showAlert(AlertType.INFORMATION, "Success", null, "Supplier added!");
    }
}