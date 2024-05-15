package com.example.ooredooshop.services.serviceImpl;

import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.UserRole;
import com.example.ooredooshop.repositories.RoleRepository;
import com.example.ooredooshop.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    @Override
    public void createRole(UserRole role) {

        roleRepository.save(role);
        logger.info("Role {} is saved", role.getId());
    }

    @Override
    public UserRole updateRole(UserRole updatedRole) {

        UserRole finalUpdatedRole = updatedRole;
        UserRole existingRole = roleRepository.findById(updatedRole.getId())
         .orElseThrow(() -> new NotFoundException("Role with ID " + finalUpdatedRole.getId() + " not found"));

         updatedRole = roleRepository.save(existingRole);
         logger.info("Role {} got updated", updatedRole.getId());

        return updatedRole;
    }

    @Override
    public UserRole getRoleById(Long id) {

        UserRole role = roleRepository.findById(id)

         .orElseThrow(() -> new NotFoundException("Role with ID " + id + " not found"));
        logger.info("Role {} is fetched", role.getId());

        return null;
    }

    @Override
    public List<UserRole> getAllRolesSortedByCreationDate() {
        logger.info("Retrieving All Roles (Sorted)");
        return roleRepository.findAllByOrderByCreationDateDesc();
    }

    @Override
    public List<UserRole>  getAllRolesByCreatorIdSortedByCreationDate(Long creatorId, String name ) {

        if(name != null){
            return roleRepository.findByCreatorIdAndNameContainingIgnoreCaseOrderByCreationDate(creatorId, name);
        }
        return roleRepository.findByCreatorIdOrderByCreationDate(creatorId);
    }


    @Override
    public List<UserRole> searchRolesByName(String keyword ) {
        return roleRepository.findByNameContainingIgnoreCaseOrderByCreationDateDesc(keyword );
    }

    @Override
    public void deleteRoleById(Long id) {

         UserRole role = roleRepository.findById(id)
         .orElseThrow(() -> new NotFoundException("Role not found with ID: " + id));
         roleRepository.delete(role);
         logger.info("Role {} is deleted", role.getId());

    }

    @Override
    public void deleteMultipleRolesByIds(List<Long> ids) {

        logger.info("Batch deletion of roles with IDs: {}", ids);
        roleRepository.deleteAllById(ids);
    }
    @Override
    public long countRoles() {
        return roleRepository.count();
    }
}
