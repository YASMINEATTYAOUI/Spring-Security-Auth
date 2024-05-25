package com.example.ooredooshop.services.serviceImpl;


import com.example.ooredooshop.enumeration.Permission;
import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Privilege;
import com.example.ooredooshop.repositories.PrivilegeRepository;
import com.example.ooredooshop.services.PrivilegeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivilegeServiceImpl implements PrivilegeService {
    private final PrivilegeRepository privilegeRepository;
    private static final Logger logger = LoggerFactory.getLogger(PrivilegeServiceImpl.class);

    @Override
    public void createPrivilege(Privilege privilege) {
        privilege.setCreationDate(new Date());
        privilegeRepository.save(privilege);
        logger.info("Privilege {} is saved", privilege.getId());
    }

    @Override
    public Privilege updatePrivilege(Privilege updatedPrivilege) {

        Privilege finalUpdatedPrivilege = updatedPrivilege;
        Privilege existingPrivilege = privilegeRepository.findById(updatedPrivilege.getId())
         .orElseThrow(() -> new NotFoundException("Privilege with ID " + finalUpdatedPrivilege.getId() + " not found"));

         updatedPrivilege = privilegeRepository.save(existingPrivilege);
        logger.info("Privilege {} got updated", updatedPrivilege.getId());
        updatedPrivilege.setLastModifiedDate(new Date());
        return updatedPrivilege;
    }

    @Override
    public Privilege getPrivilegeById(Long id) {

            Privilege privilege = privilegeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Privilege with ID " + id + " not found"));
        logger.info("Privilege {} is fetched", privilege.getId());

        return privilege;
    }

    @Override
    public List<Privilege> getAllPrivilegesSortedByCreationDate() {
        logger.info("Retrieving All Privileges (Sorted)");
        return privilegeRepository.findAllByOrderByCreationDateDesc();
    }
/*
    @Override
    public List<Privilege> searchPrivilegesByName(Permission keyword ) {
        return privilegeRepository.findByNameContainingIgnoreCaseOrderByCreationDateDesc(keyword);
    }

 */

    @Override
    public void deletePrivilegeById(Long id) {

         Privilege privilege = privilegeRepository.findById(id)
         .orElseThrow(() -> new NotFoundException("Privilege not found with ID: " + id));
         privilegeRepository.delete(privilege);
        logger.info("Privilege {} is deleted", privilege.getId());

    }

    @Override
    public void deleteMultiplePrivilegesByIds(List<Long> ids) {

        logger.info("Batch deletion of privileges with IDs: {}", ids);
        privilegeRepository.deleteAllById(ids);
    }
    @Override
    public long countPrivileges() {
        return privilegeRepository.count();
    }

}
