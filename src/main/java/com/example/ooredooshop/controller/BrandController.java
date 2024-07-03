package com.example.ooredooshop.controller;

import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Brand;
import com.example.ooredooshop.models.Category;
import com.example.ooredooshop.repositories.BrandRepository;
import com.example.ooredooshop.services.BrandService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
@RequestMapping("/api/brands")
public class BrandController {

    private final BrandRepository brandRepository;
    private final BrandService brandService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Brand> saveBrand(@RequestParam("file") MultipartFile file,
            @RequestParam("name") String name, @RequestParam("description") String description) throws IOException {
        Brand brand = new Brand();
        brand.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        brand.setName(name);
        brand.setDescription(description);
        brandService.save(brand);
        return new ResponseEntity<>(brand, HttpStatus.CREATED);
    }

@PutMapping("/{brandId}")
@ResponseStatus(HttpStatus.OK)
public Brand updateBrand(@PathVariable Long brandId,
                         @RequestParam(value = "file", required = false) MultipartFile file,
                         @RequestParam("name") String name,
                         @RequestParam("description") String description) throws IOException {
    Brand existingBrand = brandRepository.findById(brandId)
            .orElseThrow(() -> new RuntimeException("Brand not found"));

    if (file != null && !file.isEmpty()) {
        existingBrand.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
    }
    existingBrand.setName(name);
    existingBrand.setDescription(description);
    return brandService.updateBrand(existingBrand);
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
    public List<Brand> searchBrandsByKeyword(
            @RequestParam String keyword
    ) {
        return brandService.searchBrandsByName(keyword);
    }

     @GetMapping("/category")
     public List<Brand> searchBrandsByCategory(
     @RequestParam Category category) {
     return brandService.getBrandsByCategory(category);
     }

     @GetMapping("/category/reference")
     public List<Brand> searchBrandsByCategoryAndReference(
     @RequestParam Category category, String reference) {
     return brandService.getBrandsByCategoryAndName(category,reference);
     }




    @GetMapping("/count")
    public long countBrands(){
        return brandService.countBrands();
    }

}
