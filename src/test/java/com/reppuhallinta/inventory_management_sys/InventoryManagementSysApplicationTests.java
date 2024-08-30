package com.reppuhallinta.inventory_management_sys;

import com.reppuhallinta.inventory_management_sys.model.Products;
import com.reppuhallinta.inventory_management_sys.model.Suppliers;
import com.reppuhallinta.inventory_management_sys.model.User;
import com.reppuhallinta.inventory_management_sys.service.ProductService;
import com.reppuhallinta.inventory_management_sys.service.SupplierService;
import com.reppuhallinta.inventory_management_sys.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

@SpringBootTest
@Transactional
class InventoryManagementSysApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SupplierService supplierService;

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

        // Create a new supplier
        Suppliers supplier = new Suppliers();
        supplier.setSupplierName("Testing Supplier");
        supplier.setContactEmail("Testing@Supplier.com");
        Suppliers savedSupplier = supplierService.createSupplier(supplier);
        // Create a new product
        Products product = new Products();
        product.setProductName("Testing Product");
        product.setPrice(BigDecimal.valueOf(19.99));
        product.setSupplierID(savedSupplier.getSupplierID());
        product.setQuantity(10);
        product.setCategory("Testing");
        // Save the product
        Products savedProduct = productService.createProduct(product);
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
        // Verify the supplier and product were deleted
        assertThat(supplierService.getSupplierById(savedSupplier.getSupplierID())).isNull();
        assertThat(productService.getProductById(savedProduct.getId())).isNull();
    }
}