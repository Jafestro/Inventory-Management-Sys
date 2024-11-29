package com.reppuhallinta.inventory_management_sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reppuhallinta.inventory_management_sys.model.Category;


/**
 * Repository interface for managing Category entities.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
}