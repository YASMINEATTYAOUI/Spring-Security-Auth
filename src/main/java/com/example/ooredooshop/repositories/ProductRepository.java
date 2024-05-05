package com.example.ooredooshop.repositories;

import com.example.ooredooshop.models.Category;
import com.example.ooredooshop.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product> findAllByOrderByCreationDateDesc(Pageable pageable);
    Page<Product> findByReferenceContainingIgnoreCaseOrderByCreationDateDesc(String reference, Pageable pageable);
    Page<Product> findByCreatorIdOrderByCreationDate(Long creatorId, Pageable pageable);
    Page<Product> findByCreatorIdAndReferenceContainingIgnoreCaseOrderByCreationDate(Long creatorId, String reference, Pageable pageable);
    long countByTagsContainingIgnoreCase(String tag);
    Page<Product> findByCategoryOrderByCreationDateDesc(Category category, Pageable pageable);
    Page<Product> findByCategoryAndReferenceContainingIgnoreCaseOrderByCreationDateDesc(Category category, String reference, Pageable pageable);
}
