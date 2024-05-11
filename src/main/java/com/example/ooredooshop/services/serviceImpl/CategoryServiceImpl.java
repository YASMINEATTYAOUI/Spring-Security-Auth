package com.example.ooredooshop.services.serviceImpl;

import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Brand;
import com.example.ooredooshop.models.Category;
import com.example.ooredooshop.repositories.CategoryRepository;
import com.example.ooredooshop.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public Category createCategory(Category category) {
        System.out.println(category);
         Category c=categoryRepository.save(category);
        logger.info("Category {} is saved", category.getId());
        return c;
    }

    @Override
    public Category updateCategory(Category updatedCategory) {

        Category finalUpdatedCategory = updatedCategory;
        Category existingCategory = categoryRepository.findById(updatedCategory.getId())
         .orElseThrow(() -> new NotFoundException("Category with ID " + finalUpdatedCategory.getId() + " not found"));

         updatedCategory = categoryRepository.save(existingCategory);
        logger.info("Category {} got updated", updatedCategory.getId());

        return updatedCategory;
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
    public List<Category>  getAllCategoriesByCreatorIdSortedByCreationDate(Long creatorId, String name) {

        if(name != null){
            return categoryRepository.findByCreatorIdAndNameContainingIgnoreCaseOrderByCreationDate(creatorId, name);
        }
        return categoryRepository.findByCreatorIdOrderByCreationDate(creatorId);
    }
/*
    @Override
    public List<Category> getCategoriesByBrand(Brand brand) {
        return categoryRepository.findByBrandOrderByCreationDateDesc(brand);
    }

    @Override
    public List<Category> getCategoriesByBrandAndName(Brand brand, String name) {
        logger.info("Retrieving All Categories By Brand And Name ");
        return categoryRepository.findByBrandAndNameContainingIgnoreCaseOrderByCreationDateDesc(brand,name);
    }

 */

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
