package com.reppuhallinta.inventory_management_sys.service;


import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.reppuhallinta.inventory_management_sys.model.Transaction;
import com.reppuhallinta.inventory_management_sys.repository.TransactionRepository;

import jakarta.transaction.Transactional;

/**
 * Service class for managing transactions.
 */
@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

     /**
     * Constructor for TransactionService.
     * 
     * @param transactionRepository the repository for transaction data
     */
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Creates a new transaction.
     * 
     * @param transaction the transaction to create
     * @return the created transaction
     */
    public Transaction createTransaction(Transaction transaction) {
        logger.info("Transaction creation called, transaction: {}", transaction);
        Transaction createdTransaction = transactionRepository.save(transaction);
        logger.info("Transaction created: {}", createdTransaction);
        return createdTransaction;
    }

    /**
     * Retrieves all transactions.
     * 
     * @return a list of all transactions
     */
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    /**
     * Retrieves a transaction by its ID.
     * 
     * @param id the ID of the transaction
     * @return the transaction with the given ID, or null if not found
     */
    public Transaction getTransactionById(int id) {
        return transactionRepository.findById(id).orElse(null); // May need to change this
    }

     /**
     * Updates an existing transaction.
     * 
     * @param id the ID of the transaction to update
     * @param transactionDetails the new details for the transaction
     * @return the updated transaction
     */
    @Transactional
    public Transaction updateTransaction(int id, Transaction transactionDetails) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        transaction.setTransactionType(transactionDetails.getTransactionType());
        transaction.setQuantity(transactionDetails.getQuantity());
        transaction.setUserId(transactionDetails.getUserId());
        return transactionRepository.save(transaction);
    }

     /**
     * Deletes a transaction by its ID.
     * 
     * @param id the ID of the transaction to delete
     */
    public void removeTransaction(int id) {
        Transaction transaction = getTransactionById(id);
        transactionRepository.delete(transaction);
    }

    /**
     * Deletes transactions by product ID.
     * 
     * @param productId the ID of the product whose transactions to delete
     */
    @Transactional
    public void removeTransactionByProductId(int productId) {
        transactionRepository.deleteTransactionByProductId(productId);
    }

    /**
     * Retrieves transactions by product ID.
     * 
     * @param productId the ID of the product
     * @return a list of transactions for the given product ID
     */
    public List<Transaction> getTransactionsByProductId(int productId) {
        return transactionRepository.findByProductId(productId);
    }

    /**
     * Retrieves transactions by user ID.
     * 
     * @param userId the ID of the user
     * @return a list of transactions for the given user ID
     */
    public List<Transaction> getTransactionsByUserId(int userId) {
        return transactionRepository.findByUserId(userId);
    }

    /**
     * Retrieves transactions within a date range.
     * 
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return a list of transactions within the given date range
     */
    public List<Transaction> getTransactionsByDateRange(Date startDate, Date endDate) {
        return transactionRepository.findByTransactionDateBetween(startDate, endDate);
    }
}
