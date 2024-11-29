package com.reppuhallinta.inventory_management_sys.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reppuhallinta.inventory_management_sys.model.Transaction;

import jakarta.transaction.Transactional;

/**
 * Repository interface for managing Transaction entities.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    /**
     * Deletes transactions by product ID.
     * 
     * @param productId the ID of the product whose transactions to delete
     */
    @Transactional
    void deleteTransactionByProductId(int productId);
    
    /**
     * Finds transactions by product ID.
     * 
     * @param productId the ID of the product
     * @return a list of transactions for the given product ID
     */
    List<Transaction> findByProductId(int productId);

    /**
     * Finds transactions by user ID.
     * 
     * @param userId the ID of the user
     * @return a list of transactions for the given user ID
     */
    List<Transaction> findByUserId(int userId);

    /**
     * Finds transactions within a date range.
     * 
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @return a list of transactions within the given date range
     */
    List<Transaction> findByTransactionDateBetween(Date startDate, Date endDate);
}
