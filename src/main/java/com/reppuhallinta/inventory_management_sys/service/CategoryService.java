package com.reppuhallinta.inventory_management_sys.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.reppuhallinta.inventory_management_sys.model.Category;
import com.reppuhallinta.inventory_management_sys.repository.CategoryRepository;

/**
 * Service class for managing categories.
 */
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Constructor for CategoryService.
     * 
     * @param categoryRepository the repository for category data
     */
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Constructor for CategoryService.
     * 
     * @param categoryRepository the repository for category data
     */
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * Retrieves all categories.
     * 
     * @return a list of all categories
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Retrieves all categories.
     * 
     * @return a list of all categories
     */
    public Category getCategoryById(int id) {
        return categoryRepository.findById(id).orElse(null); // May need to change this
    }

    /**
     * Retrieves all categories.
     * 
     * @return a list of all categories
     */
    public Category updateCategory(int id, Category categoryDetails) {
        Category category = getCategoryById(id);
        category.setCategoryName(categoryDetails.getCategoryName());

        return categoryRepository.save(category);
    }

    /**
     * Deletes a category by its ID.
     * 
     * @param id the ID of the category to delete
     */
    public void deleteCategory(int id) {
        Category category = getCategoryById(id);

        categoryRepository.delete(category);
    }
    
}
