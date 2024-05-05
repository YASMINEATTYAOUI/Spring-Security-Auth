package com.example.ooredooshop.repositories;

import com.example.ooredooshop.models.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<UserRole,Long> {

    Page<UserRole> findAllByOrderByCreationDateDesc(Pageable pageable);
    Page<UserRole> findByNameContainingIgnoreCaseOrderByCreationDateDesc(String name, Pageable pageable);
    Page<UserRole> findByCreatorIdOrderByCreationDate(Long creatorId, Pageable pageable);
    Page<UserRole> findByCreatorIdAndNameContainingIgnoreCaseOrderByCreationDate(Long creatorId, String name, Pageable pageable);

    UserRole findByName(String name);

}
