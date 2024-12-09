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
import static com.reppuhallinta.inventory_management_sys.utils.UIUtils.SUCCESSTITLE;
import static com.reppuhallinta.inventory_management_sys.utils.UIUtils.VALIDATETITLE;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import session.CustomSessionManager;

/**
 * Controller class for creating products.
 */
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

    /**
     * Constructor for CreateProductController.
     * 
     * @param productService the service for managing products
     * @param categoryService the service for managing categories
     * @param supplierService the service for managing suppliers
     */
    public CreateProductController(ProductService productService, CategoryService categoryService, SupplierService supplierService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.supplierService = supplierService;
    }

    /**
     * Initializes the controller class.
     */
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

    /**
     * Handles the action when the create product button is clicked.
     */
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

    /**
     * Validates the input fields.
     * 
     * @return true if the input fields are valid, false otherwise
     */
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

    /**
     * Gets the category ID based on the selected category name.
     * 
     * @return the category ID
     */
    private int getCategoryId() {
        String categoryName = categoryComboBox.getValue();
        for (Category category : categories) {
            if (category.getCategoryName().equals(categoryName)) {
                return category.getId();
            }
        }
        return 0;
    }

    /**
     * Gets the supplier ID based on the selected supplier name.
     * 
     * @return the supplier ID
     */
    private int getSupplierId() {
        String supplierName = supplierComboBox.getValue();
        for (Suppliers supplier : suppliers) {
            if (supplier.getSupplierName().equals(supplierName)) {
                return supplier.getSupplierID();
            }
        }
        return 0;
    }

    /**
     * Parses the product price from the input field.
     * 
     * @return the product price as BigDecimal, or null if invalid
     */
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

    /**
     * Parses the quantity from the input field.
     * 
     * @return the quantity as Integer, or null if invalid
     */
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

     /**
     * Creates a new product.
     * 
     * @param productPriceBigDecimal the product price
     * @param quantityInt the quantity
     * @param categoryId the category ID
     * @param supplierId the supplier ID
     * @param userId the user ID
     */
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


     /**
     * Retrieves all categories.
     * 
     * @return a list of all categories
     */
    private List<Category> getAllTransactions() {
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

    /**
     * Shows a success alert and closes the current stage.
     */
    private void success() {
        UIUtils.showAlert(AlertType.INFORMATION, SUCCESSTITLE, null, "success.productCreated");

        Stage currentStage = (Stage) createButton.getScene().getWindow();
        currentStage.close();

    }

    /**
     * Handles the action when the add new category button is clicked.
     */
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
    
    /**
     * Handles the action when the add new supplier button is clicked.
     */
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