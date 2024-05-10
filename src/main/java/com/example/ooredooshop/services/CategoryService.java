package com.example.ooredooshop.services;

import com.example.ooredooshop.models.Brand;
import com.example.ooredooshop.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    void createCategory(Category category);
    Category updateCategory(Category category);

    Category getCategoryById(Long id);
    List<Category> getAllCategoriesSortedByCreationDate();
    Page<Category> getAllCategoriesByCreatorIdSortedByCreationDate(Long creatorId, String name, Pageable pageable);

    //Page<Category> getCategoriesByBrand(Brand brand, Pageable pageable);
    //Page<Category> getCategoriesByBrandAndName(Brand brand, String name, Pageable pageable) ;
   Page<Category> searchCategoriesByName(String keyword, Pageable pageable);

    void deleteCategoryById(Long id);
    void deleteMultipleCategoriesByIds(List<Long> ids);

    long countCategories();

}
