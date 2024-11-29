package com.reppuhallinta.inventory_management_sys.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reppuhallinta.inventory_management_sys.model.Transaction;
import com.reppuhallinta.inventory_management_sys.service.TransactionService;

/**
 * REST controller for managing transactions.
 */
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    
    private final TransactionService transactionService;

    /**
     * Constructor for TransactionController.
     * 
     * @param transactionService the service for managing transactions
     */
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /**
     * Creates a new transaction.
     * 
     * @param transaction the transaction to create
     * @return the created transaction
     */
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction createdTransaction = transactionService.createTransaction(transaction);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    /**
     * Retrieves all transactions.
     * 
     * @return a list of all transactions
     */
    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    /**
     * Retrieves a transaction by its ID.
     * 
     * @param id the ID of the transaction
     * @return the transaction with the given ID, or null if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable int id) {
       Transaction transaction = transactionService.getTransactionById(id);
       return new ResponseEntity<>(transaction, HttpStatus.OK); 
    }

    /**
     * Updates an existing transaction.
     * 
     * @param id the ID of the transaction to update
     * @param transactionDetails the new details for the transaction
     * @return the updated transaction
     */
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable int id, @RequestBody Transaction transactionDetails) {
        Transaction updatedTransaction = transactionService.updateTransaction(id, transactionDetails);
        return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
    }

    /**
     * Deletes a transaction by its ID.
     * 
     * @param id the ID of the transaction to delete
     * @return a response entity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable int id) {
        transactionService.removeTransaction(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
