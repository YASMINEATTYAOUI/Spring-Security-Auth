package com.example.ooredooshop.services;

import com.example.ooredooshop.models.Privilege;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PrivilegeService {
    void createPrivilege(Privilege privilege);
    Privilege togglePrivilegeStatus(Long privilegeId);
    Privilege getPrivilegeById(Long id);
    List< Privilege> getAllPrivilegesSortedByCreationDate();
    // List< Privilege> searchPrivilegesByName(Permission keyword);

    long countPrivileges();
}
