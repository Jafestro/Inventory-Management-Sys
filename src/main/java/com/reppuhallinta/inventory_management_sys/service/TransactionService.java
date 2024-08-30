package com.reppuhallinta.inventory_management_sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reppuhallinta.inventory_management_sys.model.Transactions;
import com.reppuhallinta.inventory_management_sys.repository.TransactionRepository;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    public Transactions createTransaction(Transactions transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transactions> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transactions getTransactionById(int id) {
        return transactionRepository.findById(id).orElse(null); // May need to change this
    }

    public Transactions updateTransaction(int id, Transactions transactionDetails) {
        Transactions transaction = getTransactionById(id);
        transaction.setProductID(transactionDetails.getProductID());
        transaction.setQuantity(transactionDetails.getQuantity());
        transaction.setTransactionDate(transactionDetails.getTransactionDate());
        transaction.setTransactionType(transactionDetails.getTransactionType());
        transaction.setUserID(transactionDetails.getUserID());

        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(int id) {
        Transactions transaction = getTransactionById(id);

        transactionRepository.delete(transaction);
    }
}
