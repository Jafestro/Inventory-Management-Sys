package com.reppuhallinta.inventory_management_sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reppuhallinta.inventory_management_sys.model.Products;
import com.reppuhallinta.inventory_management_sys.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping
    public ResponseEntity<Products> createProduct(@RequestBody Products product) {
        Products createdProduct = productService.createProduct(product);

        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }
    
    @GetMapping
    public List<Products> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Products> getProductById(@PathVariable int id) {
        Products product = productService.getProductById(id);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Products> updateProduct(@PathVariable int id, Products productDetails) {
        Products updatedProduct = productService.updateProduct(id, productDetails);

        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
