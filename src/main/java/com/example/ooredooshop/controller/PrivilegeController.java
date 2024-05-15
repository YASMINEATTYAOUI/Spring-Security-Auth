package com.example.ooredooshop.controller;

import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Privilege;
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

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Privilege updatePrivilege(@RequestBody Privilege updatedPrivilege) {
        return privilegeService.updatePrivilege(updatedPrivilege);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrivilege(@PathVariable Long id) {
        try {
            privilegeService.deletePrivilegeById(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/batch")
    public ResponseEntity<Void> deleteMultiplePrivileges(@RequestParam List<Long> ids) {
        privilegeService.deleteMultiplePrivilegesByIds(ids);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public List<Privilege> searchPrivilegesByKeyword(
            @RequestParam String keyword) {
        return privilegeService.searchPrivilegesByName(keyword);
    }

    @GetMapping("/creatorId/{creatorId}")
    public List<Privilege> getAllPrivilegesByCreatorIdSortedByCreationDate(@PathVariable Long creatorId,
                                                                           @RequestParam(name = "reference", required = false) String reference){
        return privilegeService.getAllPrivilegesByCreatorIdSortedByCreationDate(creatorId, reference);
    }

    @GetMapping("/count")
    public long countRoles(){
        return privilegeService.countPrivileges();
    }

}
