package com.example.ooredooshop.services.serviceImpl;

import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Privilege;
import com.example.ooredooshop.models.UserRole;
import com.example.ooredooshop.repositories.PrivilegeRepository;
import com.example.ooredooshop.services.PrivilegeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        privilege.setLastModifiedDate(new Date());

        privilegeRepository.save(privilege);
        logger.info("Privilege {} is saved", privilege.getId());
    }

    @Override
    @Transactional
    public Privilege togglePrivilegeStatus(Long privilegeId) {
        Privilege privilege = privilegeRepository.findById(privilegeId)
                .orElseThrow(() -> new NotFoundException("Privilege not found"));

        // Toggle the active status of the role
        privilege.setActive(!privilege.isActive());

        return privilegeRepository.save(privilege);
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
    public long countPrivileges() {
        return privilegeRepository.count();
    }

}
