package com.example.ooredooshop.services;

import com.example.ooredooshop.models.Privilege;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PrivilegeService {
    void createPrivilege(Privilege privilege);
    Privilege updatePrivilege(Privilege privilege);
    Privilege getPrivilegeById(Long id);
    Page< Privilege> getAllPrivilegesSortedByCreationDate(Pageable pageable);
    Page< Privilege> getAllPrivilegesByCreatorIdSortedByCreationDate(Long creatorId, String name, Pageable pageable);
    Page< Privilege> searchPrivilegesByName(String keyword, Pageable pageable);

    void deletePrivilegeById(Long id);
    void deleteMultiplePrivilegesByIds(List<Long> ids);

    long countPrivileges();
}
