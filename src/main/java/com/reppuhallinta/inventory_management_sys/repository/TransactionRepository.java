package com.reppuhallinta.inventory_management_sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reppuhallinta.inventory_management_sys.model.Transaction;

import jakarta.transaction.Transactional;


import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>{

    @Transactional
    void deleteTransactionByProductId(int productId);

    List<Transaction> findByProductId(int productId);

    List<Transaction> findByUserId(int userId);

    List<Transaction> findByTransactionDateBetween(Date startDate, Date endDate);
}
