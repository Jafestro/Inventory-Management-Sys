package com.reppuhallinta.inventory_management_sys.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reppuhallinta.inventory_management_sys.model.Category;
import com.reppuhallinta.inventory_management_sys.service.CategoryService;

/**
 * REST controller for managing categories.
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    
    private final CategoryService categoryService;

    /**
     * Constructor for CategoryController.
     * 
     * @param categoryService the service for managing categories
     */
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Creates a new category.
     * 
     * @param category the category to create
     * @return the created category
     */
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.createCategory(category);

        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    /**
     * Retrieves all categories.
     * 
     * @return a list of all categories
     */
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    /**
     * Retrieves a category by its ID.
     * 
     * @param id the ID of the category
     * @return the category with the given ID, or null if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable int id) {
        Category category = categoryService.getCategoryById(id);

        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    /**
     * Updates an existing category.
     * 
     * @param id the ID of the category to update
     * @param categoryDetails the new details for the category
     * @return the updated category
     */
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable int id, @RequestBody Category categoryDetails) {
        Category updatedCategory = categoryService.updateCategory(id, categoryDetails);

        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    /**
     * Deletes a category by its ID.
     * 
     * @param id the ID of the category to delete
     * @return a response entity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
