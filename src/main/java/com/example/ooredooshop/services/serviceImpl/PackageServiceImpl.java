package com.example.ooredooshop.services.serviceImpl;

import com.example.ooredooshop.models.Package;
import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.repositories.PackageRepository;
import com.example.ooredooshop.services.PackageService;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PackageServiceImpl implements PackageService {

    private final PackageRepository packageRepository;
    private static final Logger logger = LoggerFactory.getLogger(PackageServiceImpl.class);
    public void createPackage(Package aPackage){
        aPackage.setCreationDate(new Date());
        aPackage.setLastModifiedDate(new Date());
        packageRepository.save(aPackage);
    }
    public Package updatePackage(Package updatePackage){
        packageRepository.save(updatePackage);
        updatePackage.setLastModifiedDate(new Date());
        return updatePackage;
    }
    @Transactional
    public Package updatePackage(Long packageId, Package updatedPackage) {
        // Fetch the existing package
        Package existingPackage = packageRepository.findById(packageId)
                .orElseThrow(() -> new NotFoundException("Package not found"));

        // Update the package's fields
        existingPackage.setReference(updatedPackage.getReference());
        existingPackage.setDescription(updatedPackage.getDescription());
        existingPackage.setNbProduct(updatedPackage.getNbProduct());
        existingPackage.setPrice(updatedPackage.getPrice());
        existingPackage.setSoldQuantity(updatedPackage.getSoldQuantity());
        existingPackage.setAvailableQuantity(updatedPackage.getAvailableQuantity());
        existingPackage.setImage(updatedPackage.getImage());
        existingPackage.setLastModifiedDate(new Date());

        // Save the updated package
        return packageRepository.save(existingPackage);
    }
    public Package getPackageById(Long id){
        Package aPackage = packageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Package with ID " + id + " not found"));
        //logger.info("Package {} is fetched",aPackage.getId());

        return aPackage;

    }
    public List<Package> getAllPackagesSortedByCreationDate(){
        return packageRepository.findAllByOrderByCreationDateDesc();

    }

    public List<Package> searchPackagesByReference(String keyword){
        return packageRepository.findByReferenceContainingIgnoreCaseOrderByCreationDateDesc(keyword);

    }

    public void deletePackageById(Long id){
        Package aPackage = packageRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Package not found with ID: " + id));
        packageRepository.delete(aPackage);

    }
    public void deleteMultiplePackagesByIds(List<Long> ids){

        packageRepository.deleteAllById(ids);
    }

    public long countPackages(){

        return packageRepository.count();
    }
}
