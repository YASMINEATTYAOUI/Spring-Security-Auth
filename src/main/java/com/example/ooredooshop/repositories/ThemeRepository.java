package com.example.ooredooshop.repositories;


import com.example.ooredooshop.models.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThemeRepository extends JpaRepository<Theme,Long> {

    List<Theme> findAllByOrderByCreationDateDesc();
    List<Theme> findByNameContainingIgnoreCaseOrderByCreationDateDesc(String name);

}
