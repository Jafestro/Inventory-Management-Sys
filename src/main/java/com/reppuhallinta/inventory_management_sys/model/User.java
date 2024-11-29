package com.reppuhallinta.inventory_management_sys.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity class representing a user.
 */
@Entity
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    private int id;
    @Column(name = "Username")
    private String username;
    @Column(name = "FirstName")
    private String firstName;
    @Column(name = "LastName")
    private String lastName;
    @Column(name = "Password")
    private String password;
    @Column(name = "AccessLevel")
    private String accessLevel;

    /**
     * Constructor for User.
     * 
     * @param username the username of the user
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param password the password of the user
     * @param accessLevel the access level of the user
     */
    public User(String username, String firstName, String lastName, String password, String accessLevel) {
        super();
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.accessLevel = accessLevel;
    }
    
    /**
     * Default constructor for User.
     */
    public User() {

    }

     /**
     * Gets the ID of the user.
     * 
     * @return the ID of the user
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the user.
     * 
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the username of the user.
     * 
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

     /**
     * Sets the username of the user.
     * 
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * Gets the first name of the user.
     * 
     * @return the first name of the user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     * 
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    /**
     * Gets the last name of the user.
     * 
     * @return the last name of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     * 
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the password of the user.
     * 
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     * 
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the access level of the user.
     * 
     * @return the access level of the user
     */
    public String getAccessLevel() {
        return accessLevel;
    }

    /**
     * Sets the access level of the user.
     * 
     * @param accessLevel the access level to set
     */
    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    /**
     * Returns a string representation of the user.
     * 
     * @return a string representation of the user
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", accessLevel='" + accessLevel + '\'' +
                '}';
    }
}