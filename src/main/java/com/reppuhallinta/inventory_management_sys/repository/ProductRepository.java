package com.reppuhallinta.inventory_management_sys.repository;

import com.reppuhallinta.inventory_management_sys.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {

    Optional<Products> findById(int id);
}
