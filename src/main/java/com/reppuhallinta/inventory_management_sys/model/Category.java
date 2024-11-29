package com.reppuhallinta.inventory_management_sys.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entity class representing a category.
 */
@Entity
@Table(name = "Category")
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int categoryId;

    @Column(name = "Name")
    private String categoryName;

    /**
     * Default constructor for Category.
     */
    public Category() {
    }

    /**
     * Constructor for Category.
     * 
     * @param categoryName the name of the category
     */
    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Gets the category ID.
     * 
     * @return the category ID
     */
    public int getId() {
        return categoryId;
    }

    /**
     * Sets the category ID.
     * 
     * @param id the category ID to set
     */
    public void setId(int id) {
        this.categoryId = id;
    }

    /**
     * Gets the category name.
     * 
     * @return the category name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Sets the category name.
     * 
     * @param name the category name to set
     */
    public void setCategoryName(String name) {
        this.categoryName = name;
    }

    /**
     * Returns a string representation of the category.
     * 
     * @return a string representation of the category
     */
    @Override
    public String toString() {
        return "Category{" +
                "id=" + categoryId +
                ", name='" + categoryName + '\'' +
                '}';
    }
}