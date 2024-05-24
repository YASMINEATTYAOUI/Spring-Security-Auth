package com.example.ooredooshop.repositories;

import com.example.ooredooshop.models.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<UserRole,Long> {

    List<UserRole> findAllByOrderByCreationDateDesc();
    List<UserRole> findByNameContainingIgnoreCaseOrderByCreationDateDesc(String name);

    UserRole findByName(String name);

}
