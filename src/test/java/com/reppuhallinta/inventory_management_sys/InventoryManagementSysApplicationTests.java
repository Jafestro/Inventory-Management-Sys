package com.reppuhallinta.inventory_management_sys;

import com.reppuhallinta.inventory_management_sys.model.Category;
import com.reppuhallinta.inventory_management_sys.model.Products;
import com.reppuhallinta.inventory_management_sys.model.Suppliers;
import com.reppuhallinta.inventory_management_sys.model.Transaction;
import com.reppuhallinta.inventory_management_sys.model.User;
import com.reppuhallinta.inventory_management_sys.service.CategoryService;
import com.reppuhallinta.inventory_management_sys.service.ProductService;
import com.reppuhallinta.inventory_management_sys.service.SupplierService;
import com.reppuhallinta.inventory_management_sys.service.TransactionService;
import com.reppuhallinta.inventory_management_sys.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootTest
@Transactional
class InventoryManagementSysApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CategoryService categoryService;

    @Test
    void testUser() {
        // Create a new user
        User user = new User();
        user.setUsername("testuser");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setPassword("password");
        user.setAccessLevel("1");

        // Save the user
        User savedUser = userService.createUser(user);

        // Verify the user was saved
        assertThat(savedUser.getId()).isNotNull();
        assertThat(userService.getUserById(savedUser.getId())).isNotNull();

        // Retrieve the user
        User retrievedUser = userService.getUserById(savedUser.getId());
        assertThat(retrievedUser).isNotNull();
        assertThat(retrievedUser.getUsername()).isEqualTo("testuser");

        // Update the user
        retrievedUser.setFirstName("Updated");
        User updatedUser = userService.updateUser(retrievedUser.getId(), retrievedUser);
        assertThat(updatedUser.getFirstName()).isEqualTo("Updated");

        // Delete the user
        userService.removeUser(updatedUser.getId());
        assertThat(userService.getUserById(updatedUser.getId())).isNull();

    }

    @Test
    void testProductAndSupplier() {
        // Create a new user
        User user = new User();
        user.setUsername("testuser");
        user.setFirstName("Test");
        user.setLastName("User");
        user.setPassword("password");
        user.setAccessLevel("1");
        User savedUser = userService.createUser(user);

        // Create a new supplier
        Suppliers supplier = new Suppliers();
        supplier.setSupplierName("Testing Supplier");
        supplier.setContactEmail("Testing@Supplier.com");
        Suppliers savedSupplier = supplierService.createSupplier(supplier);

        // Create a new product
        Products product = new Products();
        product.setProductName("Testing Product");
        product.setPrice(BigDecimal.valueOf(19.99));
        product.setCategoryId(1);
        product.setSupplierID(savedSupplier.getSupplierID());
        product.setQuantity(10);

        // Save the product
        Products savedProduct = productService.createProduct(product, savedUser.getId());

        // Verify the product and supplier were saved
        assertThat(savedProduct.getId()).isNotNull();
        assertThat(savedSupplier.getSupplierID()).isNotNull();

        // Retrieve the product and supplier
        Products retrievedProduct = productService.getProductById(savedProduct.getId());
        Suppliers retrievedSupplier = supplierService.getSupplierById(savedSupplier.getSupplierID());

        // Update the product and supplier
        retrievedProduct.setProductName("Updated Product");
        retrievedSupplier.setSupplierName("Updated Supplier");

        // Delete the product and supplier
        productService.deleteProduct(retrievedProduct.getId());
        supplierService.deleteSupplier(retrievedSupplier.getSupplierID());

        // Verify the product and supplier were deleted
        assertThat(productService.getProductById(retrievedProduct.getId())).isNull();
        assertThat(supplierService.getSupplierById(retrievedSupplier.getSupplierID())).isNull();
    }

    @Test
    void testCategory() {
        Category newCategory = new Category();
        newCategory.setCategoryName("Test Category1");

        Category savedCategory = categoryService.createCategory(newCategory);

        System.out.println("Saved category: " + savedCategory);
        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getId());

        Category retrievedCategory = categoryService.getCategoryById(savedCategory.getId());
        assertThat(retrievedCategory).isNotNull();
        assertThat(retrievedCategory.getId()).isEqualTo(newCategory.getId());
        assertThat(retrievedCategory.getCategoryName()).isEqualTo(newCategory.getCategoryName());

        System.out.println("Retrieved category: " + retrievedCategory);

        retrievedCategory.setCategoryName("Updated Category1");
        Category updatedCategory = categoryService.updateCategory(retrievedCategory.getId(), retrievedCategory);
        assertThat(updatedCategory).isNotNull();
        assertThat(updatedCategory.getCategoryName()).isEqualTo("Updated Category1");

        System.out.println("Updated category: " + updatedCategory);

        System.out.println("List of all categories: " + categoryService.getAllCategories());

        categoryService.deleteCategory(updatedCategory.getId());
        Category deletedCategory = categoryService.getCategoryById(updatedCategory.getId());
        assertThat(deletedCategory).isNull();

        System.out.println("All categories after deletion: " + categoryService.getAllCategories());
    }

    @Test
void testTransaction() {
    // Create a new transaction
    Transaction newTransaction = new Transaction();
    newTransaction.setProductId(18); // Set appropriate product ID
    newTransaction.setQuantity(10);
    newTransaction.setTransactionDate(new Date());
    newTransaction.setTransactionType("SALE");
    newTransaction.setUserId(3); // Set appropriate user ID

    Transaction savedTransaction = transactionService.createTransaction(newTransaction);
    //log the saved transaction
    System.out.println("Saved transaction: " + savedTransaction);
    assertThat(savedTransaction).isNotNull();
    assertThat(savedTransaction.getTransactionId()).isNotNull();

    // Retrieve the transaction
    Transaction retrievedTransaction = transactionService.getTransactionById(savedTransaction.getTransactionId());
    assertThat(retrievedTransaction).isNotNull();
    assertThat(retrievedTransaction.getProductId()).isEqualTo(newTransaction.getProductId());
    assertThat(retrievedTransaction.getQuantity()).isEqualTo(newTransaction.getQuantity());
    assertThat(retrievedTransaction.getTransactionDate()).isEqualTo(newTransaction.getTransactionDate());
    assertThat(retrievedTransaction.getTransactionType()).isEqualTo(newTransaction.getTransactionType());
    assertThat(retrievedTransaction.getUserId()).isEqualTo(newTransaction.getUserId());

    System.out.println("Retrieved transaction: " + retrievedTransaction);

    // Update the transaction
    retrievedTransaction.setQuantity(20);
    Transaction updatedTransaction = transactionService.updateTransaction(retrievedTransaction.getTransactionId(), retrievedTransaction);
    assertThat(updatedTransaction).isNotNull();
    assertThat(updatedTransaction.getQuantity()).isEqualTo(20);

    System.out.println("Updated transaction: " + updatedTransaction);

    System.out.println("List of all transactions: " + transactionService.getAllTransactions());

    // Delete the transaction
    transactionService.removeTransaction(updatedTransaction.getTransactionId());
    Transaction deletedTransaction = transactionService.getTransactionById(updatedTransaction.getTransactionId());
    assertThat(deletedTransaction).isNull();
}

}