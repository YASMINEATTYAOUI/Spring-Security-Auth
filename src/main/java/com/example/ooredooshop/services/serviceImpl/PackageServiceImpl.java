package com.example.ooredooshop.services.serviceImpl;

import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.repositories.PackageRepository;
import com.example.ooredooshop.services.PackageService;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PackageServiceImpl implements PackageService {

    private final PackageRepository packageRepository;
    private static final Logger logger = LoggerFactory.getLogger(PackageServiceImpl.class);
    public void createPackage(Package aPackage){
        packageRepository.save(aPackage);
    }
    public Package updatePackage(Package updatePackage){
        packageRepository.save(updatePackage);
        return updatePackage;
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
    public List<Package> getAllPackagesByCreatorIdSortedByCreationDate(Long creatorId, String name){
        if(name != null){
            return packageRepository.findByCreatorIdAndReferenceContainingIgnoreCaseOrderByCreationDate(creatorId, name);
        }
        return packageRepository.findByCreatorIdOrderByCreationDate(creatorId);

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
