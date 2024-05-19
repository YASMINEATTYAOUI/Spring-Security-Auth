package com.example.ooredooshop.controller;

import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Brand;
import com.example.ooredooshop.services.PackageService;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import com.example.ooredooshop.models.Package;
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
@RequestMapping("/api/packages")
public class PackageController {

    private final PackageService packageService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Package> createPackage(@RequestParam("file") MultipartFile file,
                                           @RequestParam("reference") String reference,
                                           @RequestParam("description") String description,
                                           Integer nbProduct,
                                           @RequestParam("price") Float price,
                                           @RequestParam("soldQuantity")Integer soldQuantity,
                                           @RequestParam("availableQuantity")Integer availableQuantity) throws IOException {
        Package aPackage = new Package();
        aPackage.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        aPackage.setReference(reference);
        aPackage.setDescription(description);
        aPackage.setNbProduct(nbProduct);
        aPackage.setPrice(price);
        aPackage.setSoldQuantity(soldQuantity);
        aPackage.setAvailableQuantity(availableQuantity);
        packageService.createPackage(aPackage);
        return new ResponseEntity<>(aPackage, HttpStatus.CREATED);
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

    @GetMapping("/creatorId/{creatorId}")
    public List<Package> getAllPackagesByCreatorIdSortedByCreationDate(@PathVariable Long creatorId,
                                                                         @RequestParam(name = "reference", required = false) String reference){
        return packageService.getAllPackagesByCreatorIdSortedByCreationDate(creatorId, reference);
    }

    @GetMapping("/count")
    public long countPackages(){
        return packageService.countPackages();
    }


}
