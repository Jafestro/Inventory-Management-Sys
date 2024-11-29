package com.reppuhallinta.inventory_management_sys.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reppuhallinta.inventory_management_sys.model.Products;
import com.reppuhallinta.inventory_management_sys.service.ProductService;

/**
 * REST controller for managing products.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    final
    ProductService productService;

    /**
     * Constructor for ProductController.
     * 
     * @param productService the service for managing products
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Creates a new product.
     * 
     * @param product the product to create
     * @param userId the ID of the user creating the product
     * @return the created product
     */
    @PostMapping
    public ResponseEntity<Products> createProduct(@RequestBody Products product, @RequestParam int userId) {
        Products createdProduct = productService.createProduct(product, userId);

        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }
    
    /**
     * Retrieves all products.
     * 
     * @return a list of all products
     */
    @GetMapping
    public List<Products> getAllProducts() {
        return productService.getAllProducts();
    }

    /**
     * Retrieves a product by its ID.
     * 
     * @param id the ID of the product
     * @return the product with the given ID, or null if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Products> getProductById(@PathVariable int id) {
        Products product = productService.getProductById(id);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /**
     * Updates an existing product.
     * 
     * @param id the ID of the product to update
     * @param userId the ID of the user updating the product
     * @param productDetails the new details for the product
     * @return the updated product
     */
    @PutMapping("/{id}")
    public ResponseEntity<Products> updateProduct(@PathVariable int id, @PathVariable int userId, @RequestBody Products productDetails) {
        Products updatedProduct = productService.updateProduct(id, productDetails, userId);

        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    /**
     * Deletes a product by its ID.
     * 
     * @param id the ID of the product to delete
     * @return a response entity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
