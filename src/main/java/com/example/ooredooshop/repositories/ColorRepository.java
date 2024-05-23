package com.example.ooredooshop.repositories;

import com.example.ooredooshop.models.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends JpaRepository<Color,Long > {

    List<Color> findAllByOrderByCreationDateDesc();
    List<Color> findByNameContainingIgnoreCaseOrderByCreationDateDesc(String name);
    Color findByName(String name);

}
