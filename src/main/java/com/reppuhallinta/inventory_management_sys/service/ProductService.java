package com.reppuhallinta.inventory_management_sys.service;

import java.util.List;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import com.reppuhallinta.inventory_management_sys.model.Transaction;
import com.reppuhallinta.inventory_management_sys.repository.CategoryRepository;
import com.reppuhallinta.inventory_management_sys.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import com.reppuhallinta.inventory_management_sys.model.Products;
import com.reppuhallinta.inventory_management_sys.repository.ProductRepository;
import com.reppuhallinta.inventory_management_sys.model.Suppliers;
import com.reppuhallinta.inventory_management_sys.model.Category;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    private final TransactionService transactionService;

    private final SupplierRepository supplierRepository;

    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, TransactionService transactionService, SupplierRepository supplierRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.transactionService = transactionService;
        this.supplierRepository = supplierRepository;
        this.categoryRepository = categoryRepository;
    }

    public Products createProduct(Products product, int userId) {

        Products createdProduct = productRepository.save(product);

        Transaction transaction = new Transaction();
        transaction.setProductId(createdProduct.getId());
        transaction.setQuantity(createdProduct.getQuantity());
        transaction.setTransactionType("ADD");
        transaction.setUserId(userId);
        transaction.setTransactionDate(new Date());

        transactionService.createTransaction(transaction);

        return productRepository.save(product);
    }

    public List<Products> getAllProducts() {
        List<Products> products = productRepository.findAll();
        // Haetaan Supplier IDt tuotteista
        List<Integer> supplierIds = products.stream()
                .map(Products::getSupplierID)
                .distinct()
                .collect(Collectors.toList());
        // Haetaan kategoria IDt Tuotteistas
        List<Integer> categoryIds = products.stream()
                .map(Products::getCategoryId)
                .distinct()
                .collect(Collectors.toList());
        // Haetaan toimittajien nimet ID perusteella ja tallennetaan Mappiin
        Map<Integer, String> supplierNames = supplierRepository.findAllById(supplierIds).stream()
                .collect(Collectors.toMap(Suppliers::getSupplierID, Suppliers::getSupplierName));
        // Haetaan kategorioiden nimet ID perusteella ja tallennetaan Mappiin
        Map<Integer, String> categoryNames = categoryRepository.findAllById(categoryIds).stream()
                .collect(Collectors.toMap(Category::getId, Category::getCategoryName));
        // Lisätään tuotteille toimittajien ja kategorioiden nimet
        List<Products> productsWithNames = products.stream().map(product -> {
            product.setSupplierName(supplierNames.get(product.getSupplierID()));
            product.setCategoryName(categoryNames.get(product.getCategoryId()));
            return product;
        }).collect(Collectors.toList());

        return productsWithNames;
    }

    public Products getProductById(int id) {
        return productRepository.findById(id).orElse(null); // May need to change this
    }

    public Products updateProduct(int id, Products productDetails, int userId) {
        Products product = getProductById(id);
        product.setProductName(productDetails.getProductName());
        product.setPrice(productDetails.getPrice());
        product.setQuantity(productDetails.getQuantity());
        product.setCategoryId(productDetails.getCategoryId());
        product.setSupplierID(productDetails.getSupplierID());

        Products updatedProduct = productRepository.save(product);

        Transaction transaction = new Transaction();
        transaction.setProductId(id);
        transaction.setQuantity(productDetails.getQuantity());
        transaction.setTransactionType("UPDATE");
        transaction.setTransactionDate(new Date());
        transaction.setUserId(userId);

        transactionService.createTransaction(transaction);

        return updatedProduct;

    }

    public void deleteProduct(int id) {
        Products product = getProductById(id);

        transactionService.removeTransactionByProductId(id);

        productRepository.delete(product);
    }
}
