package com.reppuhallinta.inventory_management_sys.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity class representing a product.
 */
@Entity
@Table(name = "Products")
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductID")
    private int productID;

    @Column(name = "Name")
    private String productName;

    @Column(name = "Price")
    private BigDecimal price;
    
    @Column(name = "Quantity")
    private int quantity;

    @Column(name = "SupplierID")
    private int supplierID;

    @Column(name = "CategoryId")
    private int categoryId;

    @Column(name = "SupplierName")
    private String supplierName;

    @Column(name = "CategoryName")
    private String categoryName;

    /**
     * Constructor for Products.
     * 
     * @param productName the name of the product
     * @param price the price of the product
     * @param quantity the quantity of the product
     * @param supplierID the ID of the supplier
     * @param categoryId the ID of the category
     */
    public Products(String productName, BigDecimal price, int quantity, int supplierID, int categoryId) {
        super();
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.supplierID = supplierID;
        this.categoryId = categoryId;
    }

    /**
     * Default constructor for Products.
     */
    public Products() {

    }

    /**
     * Gets the product ID.
     * 
     * @return the product ID
     */
    public int getId() {
        return productID;
    }

    /**
     * Sets the product ID.
     * 
     * @param id the product ID to set
     */
    public void setId(int id) {
        this.productID = id;
    }

    /**
     * Gets the product name.
     * 
     * @return the product name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the product name.
     * 
     * @param productName the product name to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Gets the price of the product.
     * 
     * @return the price of the product
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     * 
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets the quantity of the product.
     * 
     * @return the quantity of the product
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product.
     * 
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the supplier ID.
     * 
     * @return the supplier ID
     */
    public int getSupplierID() {
        return supplierID;
    }

    /**
     * Sets the supplier ID.
     * 
     * @param supplierID the supplier ID to set
     */
    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    /**
     * Gets the category ID.
     * 
     * @return the category ID
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Sets the category ID.
     * 
     * @param categoryId the category ID to set
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * Gets the supplier name.
     * 
     * @return the supplier name
     */
    public String getSupplierName() {
        return supplierName;
    }

    /**
     * Sets the supplier name.
     * 
     * @param supplierName the supplier name to set
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     * Gets the category name.
     * 
     * @return the category name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Sets the category name.
     * 
     * @param categoryName the category name to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Returns a string representation of the product.
     * 
     * @return a string representation of the product
     */
    @Override
    public String toString() {
        return "Products [ProductID=" + productID + ", productName=" + productName + ", price=" + price + ", quantity=" + quantity
                + ", categoryId=" + categoryId + ", supplierID=" + supplierID + "]";
    }
}