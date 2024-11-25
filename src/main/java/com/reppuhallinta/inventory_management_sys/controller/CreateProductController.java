package com.reppuhallinta.inventory_management_sys.controller;

import com.reppuhallinta.inventory_management_sys.model.*;
import com.reppuhallinta.inventory_management_sys.service.CategoryService;
import com.reppuhallinta.inventory_management_sys.service.SupplierService;
import com.reppuhallinta.inventory_management_sys.utils.UIUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import com.reppuhallinta.inventory_management_sys.service.ProductService;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import session.CustomSessionManager;

import java.math.BigDecimal;
import java.util.List;

import static com.reppuhallinta.inventory_management_sys.utils.UIUtils.*;

@Controller
public class CreateProductController {

    private final ProductService productService;

    private final CategoryService categoryService;

    private final SupplierService supplierService;

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
    private Button addNewSupplierButton;

    @FXML
    private TextField newSupplierTextField;

    @FXML
    private TextField newCategoryTextField;

    public CreateProductController(ProductService productService, CategoryService categoryService, SupplierService supplierService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.supplierService = supplierService;
    }

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
        if (!validateInputFields()) {
            return;
        }

        int categoryId = getCategoryId();
        int supplierId = getSupplierId();

        BigDecimal productPriceBigDecimal = parseProductPrice();
        if (productPriceBigDecimal == null) {
            return;
        }

        Integer quantityInt = parseQuantity();
        if (quantityInt == null) {
            return;
        }

        User user = (User) CustomSessionManager.getAttribute("user");
        if (user == null) {
            return;
        }

        createProduct(productPriceBigDecimal, quantityInt, categoryId, supplierId, user.getId());
    }

    private boolean validateInputFields() {
        String productName = this.productNameField.getText();
        String productPrice = this.productPriceField.getText();
        String quantity = this.quantityField.getText();

        if (productName.isEmpty() || productPrice.isEmpty() || quantity.isEmpty() || categoryComboBox.getValue() == null || supplierComboBox.getValue() == null) {
            UIUtils.showAlert(Alert.AlertType.ERROR, ERRORTITLE, null, "error.enterAllFieldsCorrectly");
            return false;
        }
        return true;
    }

    private int getCategoryId() {
        String categoryName = categoryComboBox.getValue();
        for (Category category : categories) {
            if (category.getCategoryName().equals(categoryName)) {
                return category.getId();
            }
        }
        return 0;
    }

    private int getSupplierId() {
        String supplierName = supplierComboBox.getValue();
        for (Suppliers supplier : suppliers) {
            if (supplier.getSupplierName().equals(supplierName)) {
                return supplier.getSupplierID();
            }
        }
        return 0;
    }

    private BigDecimal parseProductPrice() {
        String productPrice = this.productPriceField.getText();
        try {
            BigDecimal productPriceBigDecimal = new BigDecimal(productPrice);
            if (productPriceBigDecimal.compareTo(BigDecimal.ZERO) < 0) {
                UIUtils.showAlert(Alert.AlertType.ERROR, VALIDATETITLE, null, "error.productPriceMustBePositive");
                return null;
            }
            return productPriceBigDecimal;
        } catch (Exception e) {
            UIUtils.showAlert(Alert.AlertType.ERROR, VALIDATETITLE, null, "error.invalidProductPrice");
            return null;
        }
    }

    private Integer parseQuantity() {
        String quantity = this.quantityField.getText();
        try {
            int quantityInt = Integer.parseInt(quantity);
            if (quantityInt < 0) {
                UIUtils.showAlert(Alert.AlertType.ERROR, VALIDATETITLE, null, "error.productQuantityCannotBeNegative");
                return null;
            }
            return quantityInt;
        } catch (Exception e) {
            UIUtils.showAlert(Alert.AlertType.ERROR, VALIDATETITLE, null, "error.invalidQuantity");
            return null;
        }
    }

    private void createProduct(BigDecimal productPriceBigDecimal, int quantityInt, int categoryId, int supplierId, int userId) {
        try {
            Products product = new Products();
            product.setProductName(this.productNameField.getText());
            product.setPrice(productPriceBigDecimal);
            product.setQuantity(quantityInt);
            product.setCategoryId(categoryId);
            product.setSupplierID(supplierId);

            productService.createProduct(product, userId);
            success();
        } catch (Exception e) {
            UIUtils.showAlert(Alert.AlertType.ERROR, ERRORTITLE, null, "error.enterAllFieldsCorrectly");
        }
    }


    private List<Category> getAllTransactions() {
        return categoryService.getAllCategories();
    }

    private List<Suppliers> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    private void success() {
        UIUtils.showAlert(AlertType.INFORMATION, SUCCESSTITLE, null, "success.productCreated");

        Stage currentStage = (Stage) createButton.getScene().getWindow();
        currentStage.close();

    }

    @FXML
    private void handleAddNewCategory() {
    	String newCategory = newCategoryTextField.getText();

    	if (newCategory.isEmpty()) {
            UIUtils.showAlert(Alert.AlertType.ERROR, ERRORTITLE, null, "error.enterCategoryName");
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
        UIUtils.showAlert(AlertType.INFORMATION, SUCCESSTITLE, null, "success.categoryAdded");
    }

    @FXML
    private void handleAddNewSupplier() {
    	String newSupplier = newSupplierTextField.getText();

    	if (newSupplier.isEmpty()) {
            UIUtils.showAlert(Alert.AlertType.ERROR, ERRORTITLE, null, "error.enterSupplierName");
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
        UIUtils.showAlert(AlertType.INFORMATION, SUCCESSTITLE, null, "success.supplierAdded");
    }
}