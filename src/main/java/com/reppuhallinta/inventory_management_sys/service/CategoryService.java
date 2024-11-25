package com.reppuhallinta.inventory_management_sys.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.reppuhallinta.inventory_management_sys.model.Category;
import com.reppuhallinta.inventory_management_sys.repository.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(int id) {
        return categoryRepository.findById(id).orElse(null); // May need to change this
    }

    public Category updateCategory(int id, Category categoryDetails) {
        Category category = getCategoryById(id);
        category.setCategoryName(categoryDetails.getCategoryName());

        return categoryRepository.save(category);
    }

    public void deleteCategory(int id) {
        Category category = getCategoryById(id);

        categoryRepository.delete(category);
    }
    
}
