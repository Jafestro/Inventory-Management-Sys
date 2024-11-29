package com.reppuhallinta.inventory_management_sys.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

/**
 * Entity class representing a transaction.
 */
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
    
    @Transient
    private String username;

    /**
     * Constructor for Transaction.
     * 
     * @param productId the ID of the product
     * @param transactionDate the date of the transaction
     * @param quantity the quantity of the product
     * @param transactionType the type of the transaction
     * @param userId the ID of the user
     */
    public Transaction(int productId, String transactionDate, int quantity, String transactionType, int userId) {
        super();
        this.productId = productId;
        this.transactionDate = new Date();
        this.quantity = quantity;
        this.transactionType = transactionType;
        this.userId = userId;
    }

    /**
     * Default constructor for Transaction.
     */
    public Transaction() {

    }

    /**
     * Gets the transaction ID.
     * 
     * @return the transaction ID
     */
    public int getTransactionId() {
        return transactionId;
    }

    /**
     * Sets the transaction ID.
     * 
     * @param transactionId the transaction ID to set
     */
    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Gets the product ID.
     * 
     * @return the product ID
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Sets the product ID.
     * 
     * @param productId the product ID to set
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * Gets the transaction date.
     * 
     * @return the transaction date
     */
    public Date getTransactionDate() {
        return transactionDate;
    }

    /**
     * Sets the transaction date.
     * 
     * @param transactionDate the transaction date to set
     */
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
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
     * Gets the transaction type.
     * 
     * @return the transaction type
     */
    public String getTransactionType() {
        return transactionType;
    }

    /**
     * Sets the transaction type.
     * 
     * @param transactionType the transaction type to set
     */
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    /**
     * Gets the user ID.
     * 
     * @return the user ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     * 
     * @param userId the user ID to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the username.
     * 
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     * 
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns a string representation of the transaction.
     * 
     * @return a string representation of the transaction
     */
    @Override
    public String toString() {
        return "Transaction [transactionId=" + transactionId + ", productId=" + productId + ", transactionDate="
                + transactionDate + ", quantity=" + quantity + ", transactionType=" + transactionType + ", userId="
                + userId + "]";
    }
}