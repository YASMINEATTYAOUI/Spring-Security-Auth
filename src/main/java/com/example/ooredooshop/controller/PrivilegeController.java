package com.example.ooredooshop.controller;

import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Privilege;
import com.example.ooredooshop.models.UserRole;
import com.example.ooredooshop.services.PrivilegeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
@RequestMapping("/api/privileges")
public class PrivilegeController {
    private final PrivilegeService privilegeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPrivilege(@RequestBody Privilege privilege) {
        privilegeService.createPrivilege(privilege);
    }

    @PutMapping("/{privilegeId}/toggle")
    public ResponseEntity<Privilege> togglePrivilegeStatus(@PathVariable Long privilegeId) {
        Privilege updatedPrivilege = privilegeService.togglePrivilegeStatus(privilegeId);
        return ResponseEntity.ok(updatedPrivilege);
    }

    @GetMapping("/sorted")
    @ResponseStatus(HttpStatus.OK)
    public List<Privilege> getAllPrivileges() {
        return privilegeService.getAllPrivilegesSortedByCreationDate();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Privilege> getPrivilegeById(@PathVariable Long id) {
        Privilege privilege = privilegeService.getPrivilegeById(id);
        return ResponseEntity.ok(privilege);
    }
/*
    @GetMapping("/search")
    public List<Privilege> searchPrivilegesByKeyword(
            @RequestParam Permission keyword) {
        return privilegeService.searchPrivilegesByName(keyword);
    }

 */

    @GetMapping("/count")
    public long countRoles(){
        return privilegeService.countPrivileges();
    }

}
