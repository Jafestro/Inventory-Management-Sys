package com.reppuhallinta.inventory_management_sys.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TransactionID")
    private int transactionId;

    @Column(name = "ProductID")
    private int productId;

    @Column(name = "TransactionDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    @Column(name = "Quantity")
    private int quantity;

    @Column(name = "TransactionType")
    private String transactionType;

    @Column(name = "UserId")
    private int userId;

    public Transaction(int productId, String transactionDate, int quantity, String transactionType, int userId) {
        super();
        this.productId = productId;
        this.transactionDate = new Date();
        this.quantity = quantity;
        this.transactionType = transactionType;
        this.userId = userId;
    }

    public Transaction() {

    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Transactions [transactionId=" + transactionId + ", productId=" + productId + ", transactionDate="
                + transactionDate + ", quantity=" + quantity + ", transactionType=" + transactionType + ", userId="
                + userId + "]";
    }
    
}
