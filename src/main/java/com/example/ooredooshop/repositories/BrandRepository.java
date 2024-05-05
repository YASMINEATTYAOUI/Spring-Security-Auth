package com.example.ooredooshop.repositories;

import com.example.ooredooshop.models.Brand;
import com.example.ooredooshop.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Long> {
    Page<Brand> findAllByOrderByCreationDateDesc(Pageable pageable);
    Page<Brand> findByNameContainingIgnoreCaseOrderByCreationDateDesc(String name, Pageable pageable);
    Page<Brand> findByCreatorIdOrderByCreationDate(Long creatorId, Pageable pageable);
    Page<Brand> findByCreatorIdAndNameContainingIgnoreCaseOrderByCreationDate(Long creatorId, String name, Pageable pageable);

    //Page<Brand> findAllByCategoryOrderByCreationDateDesc(Category category, Pageable pageable);

    //Page<Brand> findAllByCategoryAndNameContainingIgnoreCaseOrderByCreationDateDesc(Category category, String name, Pageable pageable);

}
