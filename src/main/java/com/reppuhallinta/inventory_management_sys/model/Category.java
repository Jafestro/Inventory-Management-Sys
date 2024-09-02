package com.reppuhallinta.inventory_management_sys.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Category")
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int categoryId;

    @Column(name = "Name")
    private String categoryName;

    public Category() {
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getId() {
        return categoryId;
    }

    public void setId(int id) {
        this.categoryId = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String name) {
        this.categoryName = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + categoryId +
                ", name='" + categoryName + '\'' +
                '}';
    }
}
