package com.example.ooredooshop.repositories;


import com.example.ooredooshop.models.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository <Package,Long> {
    List<Package> findAllByOrderByCreationDateDesc();
    List<Package> findByReferenceContainingIgnoreCaseOrderByCreationDateDesc(String keyword);
}
