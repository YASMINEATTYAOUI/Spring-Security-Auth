package com.example.ooredooshop.services.serviceImpl;

import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Brand;
import com.example.ooredooshop.models.Category;
import com.example.ooredooshop.models.UserRole;
import com.example.ooredooshop.repositories.CategoryRepository;
import com.example.ooredooshop.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public Category createCategory(Category category) {
        category.setCreationDate(new Date());
        category.setLastModifiedDate(new Date());
        category =categoryRepository.save(category);
        logger.info("Category {} is saved", category.getId());
        return category;
    }

    @Override
    public Category updateCategory(Category updatedCategory) {

        Category finalUpdatedCategory = updatedCategory;
        Category existingCategory = categoryRepository.findById(updatedCategory.getId())
         .orElseThrow(() -> new NotFoundException("Category with ID " + finalUpdatedCategory.getId() + " not found"));

         updatedCategory = categoryRepository.save(existingCategory);
        logger.info("Category {} got updated", updatedCategory.getId());

        updatedCategory.setLastModifiedDate(new Date());
        return updatedCategory;
    }

    @Transactional
    public Category updateCategory(Long roleId, Category updatedCategory) {
        // Fetch the existing role
        Category existingCategory = categoryRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException("Role not found"));

        // Update the role's fields
        existingCategory.setName(updatedCategory.getName());
        existingCategory.setLastModifiedDate(new Date());

        // Save the updated role
        return categoryRepository.save(existingCategory);
    }

    @Override
    public Category getCategoryById(Long id) {

            Category category = categoryRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Category with ID " + id + " not found"));
        logger.info("Category {} is fetched", category.getId());

        return category;
    }

    @Override
    public List<Category> getAllCategoriesSortedByCreationDate() {
        logger.info("Retrieving All Brands (Sorted)");
        return categoryRepository.findAllByOrderByCreationDateDesc();
    }

    @Override
    public List<Category> searchCategoriesByName(String keyword) {
        return categoryRepository.findByNameContainingIgnoreCaseOrderByCreationDateDesc(keyword);
    }

    @Override
    public void deleteCategoryById(Long id) {

         Category category = categoryRepository.findById(id)
         .orElseThrow(() -> new NotFoundException("Category not found with ID: " + id));
         categoryRepository.delete(category);
        logger.info("Category {} is deleted", category.getId());

    }

    @Override
    public void deleteMultipleCategoriesByIds(List<Long> ids) {

        logger.info("Batch deletion of categories with IDs: {}", ids);
        categoryRepository.deleteAllById(ids);
    }
    @Override
    public long countCategories() {
        return categoryRepository.count();
    }

}
