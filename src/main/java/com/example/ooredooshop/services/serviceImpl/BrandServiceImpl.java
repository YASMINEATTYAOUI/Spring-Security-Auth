package com.example.ooredooshop.services.serviceImpl;

import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Brand;
import com.example.ooredooshop.models.Category;
import com.example.ooredooshop.repositories.BrandRepository;
import com.example.ooredooshop.services.BrandService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Override
    public void createBrand(Brand brand) {
         brandRepository.save(brand);
        logger.info("Brand {} is saved", brand.getId());
    }

    @Override
    public Brand updateBrand(Brand updatedBrand) {

        Brand finalUpdatedBrand = updatedBrand;
        Brand existingBrand = brandRepository.findById(updatedBrand.getId())
         .orElseThrow(() -> new NotFoundException("Brand with ID " + finalUpdatedBrand.getId() + " not found"));

         updatedBrand = brandRepository.save(existingBrand);
        logger.info("Brand {} got updated", updatedBrand.getId());

        return updatedBrand;
    }

    @Override
    public Brand getBrandById(Long id) {

            Brand brand = brandRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Brand with ID " + id + " not found"));
            logger.info("Brand {} is fetched", brand.getId());

        return brand;
    }

    @Override
    public Page<Brand> getAllBrandsSortedByCreationDate(Pageable pageable) {
        logger.info("Retrieving All Brands (Sorted)");
        return brandRepository.findAllByOrderByCreationDateDesc(pageable);
    }

    @Override
    public Page<Brand>  getAllBrandsByCreatorIdSortedByCreationDate(Long creatorId, String name, Pageable pageable) {

        if(name != null){
            return brandRepository.findByCreatorIdAndNameContainingIgnoreCaseOrderByCreationDate(creatorId, name, pageable);
        }
        return brandRepository.findByCreatorIdOrderByCreationDate(creatorId, pageable);
    }

     @Override
     public Page<Brand> getBrandsByCategory(Category category, Pageable pageable) {
     return null; //brandRepository.findAllByCategoryOrderByCreationDateDesc(category,pageable);
     }

     @Override
     public Page<Brand> getBrandsByCategoryAndName(Category category, String name, Pageable pageable) {
         logger.info("Retrieving All Brands By Category And Name ");
     return null; //brandRepository.findAllByCategoryAndNameContainingIgnoreCaseOrderByCreationDateDesc(category,name,pageable);
     }

    @Override
    public Page<Brand> searchBrandsByName(String keyword, Pageable pageable) {
        return brandRepository.findByNameContainingIgnoreCaseOrderByCreationDateDesc(keyword, pageable);
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
