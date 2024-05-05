package com.example.ooredooshop.services;

import com.example.ooredooshop.models.Brand;
import com.example.ooredooshop.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BrandService {
    void createBrand(Brand brandRequest);
    Brand updateBrand(Brand brand);
    Brand getBrandById(Long id);
    Page<Brand> getAllBrandsSortedByCreationDate(Pageable pageable);
    Page<Brand> getAllBrandsByCreatorIdSortedByCreationDate(Long creatorId, String name, Pageable pageable);
    Page<Brand> getBrandsByCategory(Category category, Pageable pageable);

    Page<Brand> getBrandsByCategoryAndName(Category category, String name, Pageable pageable);
    Page<Brand> searchBrandsByName(String keyword, Pageable pageable);

    void deleteBrandById(Long id);
    void deleteMultipleBrandsByIds(List<Long> ids);

    long countBrands();
}
