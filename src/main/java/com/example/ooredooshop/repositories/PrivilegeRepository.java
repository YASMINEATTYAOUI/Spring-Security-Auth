package com.example.ooredooshop.repositories;

import com.example.ooredooshop.models.Privilege;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege,Long> {
    List<Privilege> findAllByOrderByCreationDateDesc();
    List<Privilege> findByNameContainingIgnoreCaseOrderByCreationDateDesc(String name);
}
