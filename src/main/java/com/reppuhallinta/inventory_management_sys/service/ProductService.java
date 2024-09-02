package com.reppuhallinta.inventory_management_sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reppuhallinta.inventory_management_sys.model.Products;
import com.reppuhallinta.inventory_management_sys.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Products createProduct(Products product) {
        return productRepository.save(product);
    }

    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }

    public Products getProductById(int id) {
        return productRepository.findById(id).orElse(null); // May need to change this
    }

    public Products updateProduct(int id, Products productDetails) {
        Products product = getProductById(id);
        product.setProductName(productDetails.getProductName());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());
        product.setCategoryId(productDetails.getCategoryId());
        product.setSupplierID(productDetails.getSupplierID());

        return productRepository.save(product);
    }

    public void deleteProduct(int id) {
        Products product = getProductById(id);

        productRepository.delete(product);
    }
}
