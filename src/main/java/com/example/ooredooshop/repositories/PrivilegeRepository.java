package com.example.ooredooshop.repositories;

import com.example.ooredooshop.models.Privilege;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege,Long> {
    Page<Privilege> findAllByOrderByCreationDateDesc(Pageable pageable);
    Page<Privilege> findByNameContainingIgnoreCaseOrderByCreationDateDesc(String name, Pageable pageable);
    Page<Privilege> findByCreatorIdOrderByCreationDate(Long creatorId, Pageable pageable);
    Page<Privilege> findByCreatorIdAndNameContainingIgnoreCaseOrderByCreationDate(Long creatorId, String name, Pageable pageable);

}
