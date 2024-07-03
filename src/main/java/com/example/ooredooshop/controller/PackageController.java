package com.example.ooredooshop.controller;

import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Brand;
import com.example.ooredooshop.models.Product;
import com.example.ooredooshop.repositories.PackageRepository;
import com.example.ooredooshop.repositories.ProductRepository;
import com.example.ooredooshop.services.PackageService;

import lombok.AllArgsConstructor;
import com.example.ooredooshop.models.Package;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
@RequestMapping("/api/packages")
public class PackageController {
    private final PackageRepository packageRepository;
    private final PackageService packageService;
    private final ProductRepository productRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Package> createPackage(@RequestParam("file") MultipartFile file,
                                           @RequestParam("reference") String reference,
                                           @RequestParam("description") String description,
                                           Integer nbProduct,
                                           @RequestParam("price") Float price,
                                           @RequestParam("soldQuantity")Integer soldQuantity,
                                           @RequestParam("availableQuantity")Integer availableQuantity,
                                           @RequestParam("products") Set<Long> productIds) throws IOException {
        Package aPackage = new Package();
        aPackage.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        aPackage.setReference(reference);
        aPackage.setDescription(description);
        aPackage.setNbProduct(nbProduct);
        aPackage.setPrice(price);
        aPackage.setSoldQuantity(soldQuantity);
        aPackage.setAvailableQuantity(availableQuantity);
        Set<Product> products = new HashSet<>();
        for (Long productId : productIds) {
            productRepository.findById(productId).ifPresent(products::add);
        }
        aPackage.setProducts(products);
        packageService.createPackage(aPackage);
        return new ResponseEntity<>(aPackage, HttpStatus.CREATED);
    }



/*
    @PutMapping("/{packageId}")
    @ResponseStatus(HttpStatus.OK)
    public Package updatePackage(@PathVariable Long packageId,
                                 @RequestParam("file") MultipartFile file,
                                 @RequestParam("reference") String reference,
                                 @RequestParam("description") String description,
                                 @RequestParam("nbProduct") Integer nbProduct,
                                 @RequestParam("price") Float price,
                                 @RequestParam("soldQuantity") Integer soldQuantity,
                                 @RequestParam("availableQuantity") Integer availableQuantity) throws IOException {
        Package updatedPackage = new Package();
        updatedPackage.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        updatedPackage.setReference(reference);
        updatedPackage.setDescription(description);
        updatedPackage.setNbProduct(nbProduct);
        updatedPackage.setPrice(price);
        updatedPackage.setSoldQuantity(soldQuantity);
        updatedPackage.setAvailableQuantity(availableQuantity);
        return packageService.updatePackage(packageId, updatedPackage);
    }
*/

    @PutMapping("/{packageId}")
    @ResponseStatus(HttpStatus.OK)
    public Package updatePackage(@PathVariable Long packageId,
                                 @RequestParam(value = "file", required = false) MultipartFile file,
                                 @RequestParam("reference") String reference,
                                 @RequestParam("description") String description,
                                 @RequestParam("nbProduct") Integer nbProduct,
                                 @RequestParam("price") Float price,
                                 @RequestParam("soldQuantity") Integer soldQuantity,
                                 @RequestParam("availableQuantity") Integer availableQuantity,
                                 @RequestParam("products") Set<Long> productIds ) throws IOException {

        Package existingPackage = packageRepository.findById(packageId)
                .orElseThrow(() -> new RuntimeException("Package not found"));

        if (file != null) {
            existingPackage.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        }

        existingPackage.setReference(reference);
        existingPackage.setDescription(description);
        existingPackage.setNbProduct(nbProduct);
        existingPackage.setPrice(price);
        existingPackage.setSoldQuantity(soldQuantity);
        existingPackage.setAvailableQuantity(availableQuantity);
        Set<Product> products = new HashSet<>();
        for (Long productId : productIds) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product with ID " + productId + " not found"));
            products.add(product);
        }
        existingPackage.setProducts(products);

        return packageService.updatePackage(existingPackage);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Package updatePackage(@RequestBody Package updatedPackage) {
        return packageService.updatePackage(updatedPackage);
    }

    @GetMapping("/sorted")
    @ResponseStatus(HttpStatus.OK)
    public List<Package> getAllPackages() {
        return packageService.getAllPackagesSortedByCreationDate();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Package> getPackageById(@PathVariable Long id) {
        Package privilege = packageService.getPackageById(id);
        return ResponseEntity.ok(privilege);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable Long id) {
        try {
            packageService.deletePackageById(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/batch")
    public ResponseEntity<Void> deleteMultiplePackages(@RequestParam List<Long> ids) {
        packageService.deleteMultiplePackagesByIds(ids);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public List<Package> searchPackagesByKeyword(
            @RequestParam String keyword) {
        return packageService.searchPackagesByReference(keyword);
    }



    @GetMapping("/count")
    public long countPackages(){
        return packageService.countPackages();
    }


}
