package com.example.ooredooshop.services.serviceImpl;

import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Brand;
import com.example.ooredooshop.models.Category;
import com.example.ooredooshop.repositories.BrandRepository;
import com.example.ooredooshop.services.BrandService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

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
    public List<Brand> getAllBrandsSortedByCreationDate() {
        logger.info("Retrieving All Brands (Sorted)");
        return brandRepository.findAllByOrderByCreationDateDesc();
    }

    @Override
    public List<Brand>  getAllBrandsByCreatorIdSortedByCreationDate(Long creatorId, String name) {

        if(name != null){
            return brandRepository.findByCreatorIdAndNameContainingIgnoreCaseOrderByCreationDate(creatorId, name);
        }
        return brandRepository.findByCreatorIdOrderByCreationDate(creatorId);
    }

     @Override
     public List<Brand> getBrandsByCategory(Category category) {
     return null; //brandRepository.findAllByCategoryOrderByCreationDateDesc(category,pageable);
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
