package com.example.ooredooshop.repositories;

import com.example.ooredooshop.models.Brand;
import com.example.ooredooshop.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Long> {
    List<Brand> findAllByOrderByCreationDateDesc();
    List<Brand> findByNameContainingIgnoreCaseOrderByCreationDateDesc(String name);
    }
