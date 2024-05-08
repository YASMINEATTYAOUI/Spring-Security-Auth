package com.example.ooredooshop.controller;

import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.UserRole;
import com.example.ooredooshop.services.RoleService;
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
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleService roleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createRole(@RequestBody UserRole role) {
        roleService.createRole(role);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserRole updateRole(@RequestBody UserRole updatedRole) {
        return roleService.updateRole(updatedRole);
    }

    @GetMapping("/sorted")
    @ResponseStatus(HttpStatus.OK)
    public List<UserRole> getAllRoles() {
        return roleService.getAllRolesSortedByCreationDate();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRole> getRoleById(@PathVariable Long id) {
        UserRole role = roleService.getRoleById(id);
        return ResponseEntity.ok(role);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        try {
            roleService.deleteRoleById(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/batch")
    public ResponseEntity<Void> deleteMultipleRoles(@RequestParam List<Long> ids) {
        roleService.deleteMultipleRolesByIds(ids);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public Page<UserRole> searchRolesByKeyword(
            @RequestParam String keyword,
            Pageable pageable
    ) {
        return roleService.searchRolesByName(keyword, pageable);
    }

    @GetMapping("/creatorId/{creatorId}")
    public Page<UserRole> getAllRolesByCreatorIdSortedByCreationDate(@PathVariable Long creatorId,
                                                                          @RequestParam(name = "reference", required = false) String reference,
                                                                          Pageable pageable){
        return roleService.getAllRolesByCreatorIdSortedByCreationDate(creatorId, reference, pageable);
    }

    @GetMapping("/count")
    public long countRoles(){
        return roleService.countRoles();
    }
}
