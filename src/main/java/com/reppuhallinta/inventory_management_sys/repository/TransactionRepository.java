package com.reppuhallinta.inventory_management_sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reppuhallinta.inventory_management_sys.model.Transaction;

import jakarta.transaction.Transactional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>{

    @Transactional
    void deleteTransactionByProductId(int productId);

    
    
}
