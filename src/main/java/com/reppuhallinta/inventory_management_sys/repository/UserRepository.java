package com.reppuhallinta.inventory_management_sys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reppuhallinta.inventory_management_sys.model.User;

/**
 * Repository interface for managing User entities.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    
    /**
     * Finds a user by username.
     * 
     * @param username the username of the user
     * @return the user with the given username, or null if not found
     */
    User findByUsername(String username);

    /**
     * Finds users by their IDs.
     * 
     * @param userIds the list of user IDs
     * @return a list of users with the given IDs
     */
    List<User> findByIdIn(List<Integer> userIds);
}
