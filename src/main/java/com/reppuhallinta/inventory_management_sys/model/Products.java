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
    @Column(name = "Id")
    private Long id;

    @Column(name = "Name")
    private String productName;

    @Column(name = "Price")
    private BigDecimal price;
    
    @Column(name = "Quantity")
    private int quantity;

    @Column(name = "Category")
    private String category;

    @Column(name = "SupplierID")
    private int supplierID;


    public Products(String productName, BigDecimal price, int quantity, String category, int supplierID) {
        super();
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.supplierID = supplierID;
    }

    public Products() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    @Override
    public String toString() {
        return "Products [id=" + id + ", productName=" + productName + ", price=" + price + ", quantity=" + quantity
                + ", category=" + category + ", supplierID=" + supplierID + "]";
    }
}
