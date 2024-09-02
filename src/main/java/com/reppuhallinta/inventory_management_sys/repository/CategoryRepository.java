package com.reppuhallinta.inventory_management_sys.repository;

import org.springframework.stereotype.Repository;

import com.reppuhallinta.inventory_management_sys.model.Category;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
}
