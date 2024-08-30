package com.reppuhallinta.inventory_management_sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reppuhallinta.inventory_management_sys.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    

}
