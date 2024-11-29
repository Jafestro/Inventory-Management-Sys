package com.reppuhallinta.inventory_management_sys.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.reppuhallinta.inventory_management_sys.model.Category;
import com.reppuhallinta.inventory_management_sys.model.Products;
import com.reppuhallinta.inventory_management_sys.model.Suppliers;
import com.reppuhallinta.inventory_management_sys.model.Transaction;
import com.reppuhallinta.inventory_management_sys.repository.CategoryRepository;
import com.reppuhallinta.inventory_management_sys.repository.ProductRepository;
import com.reppuhallinta.inventory_management_sys.repository.SupplierRepository;

/**
 * Service class for managing products.
 */
@Service
public class ProductService {
    private final ProductRepository productRepository;

    private final TransactionService transactionService;

    private final SupplierRepository supplierRepository;

    private final CategoryRepository categoryRepository;

    /**
     * Constructor for ProductService.
     * 
     * @param productRepository the repository for product data
     * @param transactionService the service for managing transactions
     * @param supplierRepository the repository for supplier data
     * @param categoryRepository the repository for category data
     */
    public ProductService(ProductRepository productRepository, TransactionService transactionService, SupplierRepository supplierRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.transactionService = transactionService;
        this.supplierRepository = supplierRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Creates a new product and logs the transaction.
     * 
     * @param product the product to create
     * @param userId the ID of the user creating the product
     * @return the created product
     */
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

     /**
     * Retrieves all products, including their supplier and category names.
     * 
     * @return a list of all products with supplier and category names
     */
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

    /**
     * Retrieves a product by its ID.
     * 
     * @param id the ID of the product
     * @return the product with the given ID, or null if not found
     */
    public Products getProductById(int id) {
        return productRepository.findById(id).orElse(null); // May need to change this
    }

    /**
     * Updates an existing product and logs the transaction.
     * 
     * @param id the ID of the product to update
     * @param productDetails the new details for the product
     * @param userId the ID of the user updating the product
     * @return the updated product
     */
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

    /**
     * Deletes a product by its ID and removes associated transactions.
     * 
     * @param id the ID of the product to delete
     */
    public void deleteProduct(int id) {
        Products product = getProductById(id);

        transactionService.removeTransactionByProductId(id);

        productRepository.delete(product);
    }
}
