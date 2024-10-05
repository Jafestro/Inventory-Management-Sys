package com.reppuhallinta.inventory_management_sys.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
    private String supplierName; // New field

    @Column(name = "CategoryName")
    private String categoryName; // New field


    public Products(String productName, BigDecimal price, int quantity, int supplierID, int categoryId) {
        super();
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.supplierID = supplierID;
        this.categoryId = categoryId;
    }

    public Products() {

    }



    public int getId() {
        return productID;
    }

    public void setId(int id) {
        this.productID = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

    @Override
    public String toString() {
        return "Products [ProductID=" + productID + ", productName=" + productName + ", price=" + price + ", quantity=" + quantity
                + ", categoryId=" + categoryId + ", supplierID=" + supplierID + "]";
    }
}
