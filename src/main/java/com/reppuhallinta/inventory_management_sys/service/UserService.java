package com.reppuhallinta.inventory_management_sys.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.reppuhallinta.inventory_management_sys.model.User;
import com.reppuhallinta.inventory_management_sys.repository.UserRepository;

/**
 * Service class for managing users.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    /**
     * Constructor for UserService.
     * 
     * @param userRepository the repository for user data
     * @param passwordEncoder the password encoder for encoding user passwords
     */
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Creates a new user.
     * 
     * @param user the user to create
     * @return the created user
     */
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

     /**
     * Retrieves all users.
     * 
     * @return a list of all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Retrieves a user by its ID.
     * 
     * @param id the ID of the user
     * @return the user with the given ID, or null if not found
     */
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null); // May need to change this
    }

    /**
     * Updates an existing user.
     * 
     * @param id the ID of the user to update
     * @param userDetails the new details for the user
     * @return the updated user
     */
    public User updateUser(int id, User userDetails) {
        User user = getUserById(id);
        user.setUsername(userDetails.getUsername());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        user.setAccessLevel(userDetails.getAccessLevel());

        return user;
    }

    /**
     * Deletes a user by its ID.
     * 
     * @param id the ID of the user to delete
     */
    public void removeUser(int id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    /**
     * Authenticates a user by username and password.
     * 
     * @param username the username of the user
     * @param password the password of the user
     * @return the authenticated user, or null if authentication fails
     */
    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }

        return null;
    }

    /**
     * Finds a user by username.
     * 
     * @param username the username of the user
     * @return the user with the given username, or null if not found
     */
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Retrieves users by their IDs.
     * 
     * @param userIds the list of user IDs
     * @return a list of users with the given IDs
     */
    public List<User> getUsersByIds(List<Integer> userIds) {
        return userRepository.findByIdIn(userIds);
    }
}
