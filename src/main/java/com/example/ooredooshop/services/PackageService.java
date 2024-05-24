package com.example.ooredooshop.services;

import com.example.ooredooshop.models.Package;

import java.util.List;

public interface PackageService {
    void createPackage(Package aPackage);
    Package updatePackage(Package aPackage);
    Package updatePackage(Long packageId, Package updatedPackage);
    Package getPackageById(Long id);
    List<Package> getAllPackagesSortedByCreationDate();
    List<Package> searchPackagesByReference(String keyword);

    void deletePackageById(Long id);
    void deleteMultiplePackagesByIds(List<Long> ids);

    long countPackages();
}
