package com.example.ooredooshop.repositories;

import com.example.ooredooshop.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Page<Category> findAllByOrderByCreationDateDesc(Pageable pageable);
    Page<Category> findByNameContainingIgnoreCaseOrderByCreationDateDesc(String name, Pageable pageable);
    Page<Category> findByCreatorIdOrderByCreationDate(Long creatorId, Pageable pageable);
    Page<Category> findByCreatorIdAndNameContainingIgnoreCaseOrderByCreationDate(Long creatorId, String name, Pageable pageable);

    //Page<Category> findByBrandOrderByCreationDateDesc(Brand brand, Pageable pageable);

   // Page<Category> findByBrandAndNameContainingIgnoreCaseOrderByCreationDateDesc(Brand brand, String name, Pageable pageable);

}
