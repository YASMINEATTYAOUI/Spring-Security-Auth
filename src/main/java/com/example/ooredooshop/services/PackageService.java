package com.example.ooredooshop.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PackageService {
    void createPackage(Package aPackage);
    Package updatePackage(Package aPackage);
    Package getPackageById(Long id);
    List<Package> getAllPackagesSortedByCreationDate();
    List<Package> getAllPackagesByCreatorIdSortedByCreationDate(Long creatorId, String name);
    List<Package> searchPackagesByReference(String keyword);

    void deletePackageById(Long id);
    void deleteMultiplePackagesByIds(List<Long> ids);

    long countPackages();
}
