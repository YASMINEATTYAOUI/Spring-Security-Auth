package com.example.ooredooshop.repositories;

import com.example.ooredooshop.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findAllByOrderByCreationDateDesc();
    List<Category> findByNameContainingIgnoreCaseOrderByCreationDateDesc(String name);
    }
