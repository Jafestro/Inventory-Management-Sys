package com.reppuhallinta.inventory_management_sys.service;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reppuhallinta.inventory_management_sys.model.Transaction;
import com.reppuhallinta.inventory_management_sys.repository.TransactionRepository;

import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);


    public Transaction createTransaction(Transaction transaction) {
        logger.info("Transaction creation called, transaction: " + transaction);
        Transaction createdTransaction = transactionRepository.save(transaction);
        logger.info("Transaction created: " + createdTransaction);
        return createdTransaction;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(int id) {
        return transactionRepository.findById(id).orElse(null); // May need to change this
    }

    @Transactional
    public Transaction updateTransaction(int id, Transaction transactionDetails) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        transaction.setTransactionType(transactionDetails.getTransactionType());
        transaction.setQuantity(transactionDetails.getQuantity());
        transaction.setUserId(transactionDetails.getUserId());
        return transactionRepository.save(transaction);
    }

    public void removeTransaction(int id) {
        Transaction transaction = getTransactionById(id);
        transactionRepository.delete(transaction);
    }

    @Transactional
    public void removeTransactionByProductId(int productId) {
        transactionRepository.deleteTransactionByProductId(productId);
    }

    public List<Transaction> getTransactionsByProductId(int productId) {
        return transactionRepository.findByProductId(productId);
    }

    public List<Transaction> getTransactionsByUserId(int userId) {
        return transactionRepository.findByUserId(userId);
    }

    public List<Transaction> getTransactionsByDateRange(Date startDate, Date endDate) {
        return transactionRepository.findByTransactionDateBetween(startDate, endDate);
    }
}
