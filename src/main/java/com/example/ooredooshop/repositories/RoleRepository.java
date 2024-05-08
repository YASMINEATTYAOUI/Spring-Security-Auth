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
    Page<UserRole> findByNameContainingIgnoreCaseOrderByCreationDateDesc(String name, Pageable pageable);
    Page<UserRole> findByCreatorIdOrderByCreationDate(Long creatorId, Pageable pageable);
    Page<UserRole> findByCreatorIdAndNameContainingIgnoreCaseOrderByCreationDate(Long creatorId, String name, Pageable pageable);

    UserRole findByName(String name);

}
