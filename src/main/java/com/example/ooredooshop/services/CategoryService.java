package com.example.ooredooshop.services;

import com.example.ooredooshop.models.Brand;
import com.example.ooredooshop.models.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    Category updateCategory(Category category);

    Category getCategoryById(Long id);
    List<Category> getAllCategoriesSortedByCreationDate();
   List<Category> searchCategoriesByName(String keyword );

    void deleteCategoryById(Long id);
    void deleteMultipleCategoriesByIds(List<Long> ids);

    long countCategories();

}
