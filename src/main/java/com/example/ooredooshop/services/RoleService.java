package com.example.ooredooshop.services;

import com.example.ooredooshop.models.UserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {
    void createRole(UserRole roleRequest);
    UserRole updateRole(UserRole role);
    UserRole getRoleById(Long id);

    Page<UserRole> getAllRolesSortedByCreationDate(Pageable pageable);
    Page<UserRole> getAllRolesByCreatorIdSortedByCreationDate(Long creatorId, String name, Pageable pageable);
    Page<UserRole> searchRolesByName(String keyword, Pageable pageable);

    void deleteRoleById(Long id);
    void deleteMultipleRolesByIds(List<Long> ids);
    long countRoles();
}
