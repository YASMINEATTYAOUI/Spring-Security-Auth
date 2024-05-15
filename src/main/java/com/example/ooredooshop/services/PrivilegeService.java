package com.example.ooredooshop.services;

import com.example.ooredooshop.models.Privilege;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PrivilegeService {
    void createPrivilege(Privilege privilege);
    Privilege updatePrivilege(Privilege privilege);
    Privilege getPrivilegeById(Long id);
    List< Privilege> getAllPrivilegesSortedByCreationDate();
    List< Privilege> getAllPrivilegesByCreatorIdSortedByCreationDate(Long creatorId, String name);
    List< Privilege> searchPrivilegesByName(String keyword);

    void deletePrivilegeById(Long id);
    void deleteMultiplePrivilegesByIds(List<Long> ids);

    long countPrivileges();
}
