package com.reppuhallinta.inventory_management_sys.repository;

import com.reppuhallinta.inventory_management_sys.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Products, Integer> {

}
