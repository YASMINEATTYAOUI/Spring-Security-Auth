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

    /*@PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserRole updateRole(@RequestBody UserRole updatedRole) {
        return roleService.updateRole(updatedRole);
    }*/

    @PutMapping("/{roleId}/toggle")
    public ResponseEntity<UserRole> toggleRoleStatus(@PathVariable Long roleId) {
        UserRole updatedRole = roleService.toggleRoleStatus(roleId);
        return ResponseEntity.ok(updatedRole);
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
    public List<UserRole> searchRolesByKeyword(
            @RequestParam String keyword
    ) {
        return roleService.searchRolesByName(keyword);
    }

    @GetMapping("/creatorId/{creatorId}")
    public List<UserRole> getAllRolesByCreatorIdSortedByCreationDate(@PathVariable Long creatorId,
                                                                          @RequestParam(name = "name", required = false) String name){
        return roleService.getAllRolesByCreatorIdSortedByCreationDate(creatorId, name);
    }

    @GetMapping("/count")
    public long countRoles(){
        return roleService.countRoles();
    }
    @PutMapping("/{roleId}")
    @ResponseStatus(HttpStatus.OK)
    public UserRole updateRole(@PathVariable Long roleId, @RequestBody UserRole updatedRole) {
        return roleService.updateRole(roleId, updatedRole);
    }


}
