package com.reppuhallinta.inventory_management_sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reppuhallinta.inventory_management_sys.model.Transactions;
import com.reppuhallinta.inventory_management_sys.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    
    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transactions> createTransaction(Transactions transaction) {
        Transactions createdTransaction = transactionService.createTransaction(transaction);

        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    @GetMapping
    public List<Transactions> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transactions> getTransactionById(int id) {
        Transactions transaction = transactionService.getTransactionById(id);

        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Transactions> updateTransaction(int id, Transactions transactionDetails) {
        Transactions updatedTransaction = transactionService.updateTransaction(id, transactionDetails);

        return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(int id) {
        transactionService.deleteTransaction(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
