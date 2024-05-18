package com.example.ooredooshop.repositories;

import com.example.ooredooshop.models.Category;
import com.example.ooredooshop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    List<Product> findAllByOrderByCreationDateDesc();
    List<Product> findByReferenceContainingIgnoreCaseOrderByCreationDateDesc(String reference );
    List<Product> findByCreatorIdOrderByCreationDate(Long creatorId);
    List<Product> findByCreatorIdAndReferenceContainingIgnoreCaseOrderByCreationDate(Long creatorId, String reference );
    List<Product> findByCategoryOrderByCreationDateDesc(Category category );
    List<Product> findByCategoryAndReferenceContainingIgnoreCaseOrderByCreationDateDesc(Category category, String reference);
}
