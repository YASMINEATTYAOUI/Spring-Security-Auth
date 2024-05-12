package com.example.ooredooshop.services;

import com.example.ooredooshop.models.Brand;
import com.example.ooredooshop.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BrandService {

    void saveBrand(MultipartFile file, String name);
    void changeBrandName(Long id, String name) ;
    void createBrand(Brand brand);
    Brand updateBrand(Brand brand);
    Brand getBrandById(Long id);
    List<Brand> getAllBrandsSortedByCreationDate();
    List<Brand> getAllBrandsByCreatorIdSortedByCreationDate(Long creatorId, String name);
    List<Brand> getBrandsByCategory(Category category);

    List<Brand> getBrandsByCategoryAndName(Category category, String name);
    List<Brand> searchBrandsByName(String keyword);

    void deleteBrandById(Long id);
    void deleteMultipleBrandsByIds(List<Long> ids);

    long countBrands();
}

