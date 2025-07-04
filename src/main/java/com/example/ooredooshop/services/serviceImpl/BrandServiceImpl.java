package com.example.ooredooshop.services.serviceImpl;

import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Brand;
import com.example.ooredooshop.models.Category;
import com.example.ooredooshop.repositories.BrandRepository;
import com.example.ooredooshop.services.BrandService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private static final Logger logger = LoggerFactory.getLogger(BrandServiceImpl.class);

    //new methode (save image)


    public Brand  save(Brand brand ){
        brand.setCreationDate(new Date());
        brand.setLastModifiedDate(new Date());
        return brandRepository.save(brand);
    }

    public void changeBrandName(Long id, String name) {
        Brand brand = brandRepository.findById(id).get();
        brand.setName(name);
        brandRepository.save(brand);
    }

    @Override
    public Brand updateBrand(Brand updatedBrand) {

        Brand finalUpdatedBrand = updatedBrand;
        Brand existingBrand = brandRepository.findById(updatedBrand.getId())
                .orElseThrow(() -> new NotFoundException("Brand with ID " + finalUpdatedBrand.getId() + " not found"));

        updatedBrand = brandRepository.save(existingBrand);
        logger.info("Brand {} got updated", updatedBrand.getId());
        updatedBrand.setLastModifiedDate(new Date());
        return updatedBrand;
    }

    @Transactional
    public Brand updateBrand(Long brandId, Brand updatedBrand) {
        // Fetch the existing brand
        Brand existingBrand = brandRepository.findById(brandId)
                .orElseThrow(() -> new NotFoundException("Brand not found"));

        // Update the brand's fields
        existingBrand.setName(updatedBrand.getName());
        existingBrand.setDescription(updatedBrand.getDescription());
        existingBrand.setImage(updatedBrand.getImage());
        existingBrand.setLastModifiedDate(new Date());

        // Save the updated brand
        return brandRepository.save(existingBrand);
    }

    @Override
    public Brand getBrandById(Long id) {

            Brand brand = brandRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Brand with ID " + id + " not found"));
            logger.info("Brand {} is fetched", brand.getId());

        return brand;
    }

    @Override
    public List<Brand> getAllBrandsSortedByCreationDate() {
        logger.info("Retrieving All Brands (Sorted)");
        return brandRepository.findAllByOrderByCreationDateDesc();
    }

     @Override
     public List<Brand> getBrandsByCategory(Category category) {
     return null; //brandRepository.findAllByCategoryOrderByCreationDateDesc(category);
     }

     @Override
     public List<Brand> getBrandsByCategoryAndName(Category category, String name) {
         logger.info("Retrieving All Brands By Category And Name ");
     return null; //brandRepository.findAllByCategoryAndNameContainingIgnoreCaseOrderByCreationDateDesc(category,name);
     }

    @Override
    public List<Brand> searchBrandsByName(String keyword) {
        return brandRepository.findByNameContainingIgnoreCaseOrderByCreationDateDesc(keyword);
    }

    @Override
    public void deleteBrandById(Long id) {

         Brand brand = brandRepository.findById(id)
         .orElseThrow(() -> new NotFoundException("Brand not found with ID: " + id));
        brandRepository.delete(brand);
         logger.info("Brand {} is deleted", brand.getId());
    }

    @Override
    public void deleteMultipleBrandsByIds(List<Long> ids) {

        logger.info("Batch deletion of brands with IDs: {}", ids);
        brandRepository.deleteAllById(ids);
    }
    @Override
    public long countBrands() {
        return brandRepository.count();
    }


}
