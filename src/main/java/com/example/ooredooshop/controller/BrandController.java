package com.example.ooredooshop.controller;

import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Brand;
import com.example.ooredooshop.models.Category;
import com.example.ooredooshop.services.BrandService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
@RequestMapping("/api/brands")
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBrand(@RequestBody Brand brand) {
        brandService.createBrand(brand);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Brand updateBrand(@RequestBody Brand updatedBrand) {
        return brandService.updateBrand(updatedBrand);
    }

    @GetMapping("/sorted")
    @ResponseStatus(HttpStatus.OK)
    public List<Brand> getAllBrands() {
        return brandService.getAllBrandsSortedByCreationDate();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable Long id) {
        Brand product = brandService.getBrandById(id);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        try {
            brandService.deleteBrandById(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/batch")
    public ResponseEntity<Void> deleteMultipleBrands(@RequestParam List<Long> ids) {
        brandService.deleteMultipleBrandsByIds(ids);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public Page<Brand> searchBrandsByKeyword(
            @RequestParam String keyword,
            Pageable pageable
    ) {
        return brandService.searchBrandsByName(keyword, pageable);
    }

     @GetMapping("/category")
     public Page<Brand> searchBrandsByCategory(
     @RequestParam Category category,
     Pageable pageable
     ) {
     return brandService.getBrandsByCategory(category, pageable);
     }

     @GetMapping("/category/reference")
     public Page<Brand> searchBrandsByCategoryAndReference(
     @RequestParam Category category, String reference,
     Pageable pageable
     ) {
     return brandService.getBrandsByCategoryAndName(category,reference, pageable);
     }


    @GetMapping("/creatorId/{creatorId}")
    public Page<Brand> getAllBrandsByCreatorIdSortedByCreationDate(@PathVariable Long creatorId,
                                                                      @RequestParam(name = "reference", required = false) String reference,
                                                                      Pageable pageable){
        return brandService.getAllBrandsByCreatorIdSortedByCreationDate(creatorId, reference, pageable);
    }

    @GetMapping("/count")
    public long countBrands(){
        return brandService.countBrands();
    }

}
