package com.reppuhallinta.inventory_management_sys.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Transactions")
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TransactionID")
    private int transactionID;

    @Column(name = "ProductID")
    private int productID;

    @Column(name = "TransactionDate")
    private String transactionDate;

    @Column(name = "Quantity")
    private int quantity;

    @Column(name = "TransactionType")
    private String transactionType;

    @Column(name = "UserID")
    private int userID;

    public Transactions(int productID, String transactionDate, int quantity, String transactionType, int userID) {
        super();
        this.productID = productID;
        this.transactionDate = transactionDate;
        this.quantity = quantity;
        this.transactionType = transactionType;
        this.userID = userID;
    }

    public Transactions() {

    }

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Transactions [transactionID=" + transactionID + ", productID=" + productID + ", transactionDate="
                + transactionDate + ", quantity=" + quantity + ", transactionType=" + transactionType + ", userID="
                + userID + "]";
    }
}
