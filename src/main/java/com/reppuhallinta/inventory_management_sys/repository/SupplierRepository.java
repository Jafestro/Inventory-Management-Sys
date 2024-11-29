package com.reppuhallinta.inventory_management_sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reppuhallinta.inventory_management_sys.model.Suppliers;

/**
 * Repository interface for managing Supplier entities.
 */
@Repository
public interface SupplierRepository extends JpaRepository<Suppliers, Integer> {
    
}