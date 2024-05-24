package com.example.ooredooshop.controller;

import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Theme;
import com.example.ooredooshop.services.ThemeService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
@RequestMapping("/api/themes")
public class ThemeController {

    private final ThemeService themeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTheme(@RequestBody Theme theme) {
        themeService.createTheme(theme);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Theme updateTheme(@RequestBody Theme updatedTheme) {
        return themeService.updateTheme(updatedTheme);
    }

    @PutMapping("/{themeId}")
    @ResponseStatus(HttpStatus.OK)
    public Theme updateTheme(@PathVariable Long themeId, @RequestBody Theme updatedTheme) {
        return themeService.updateTheme(themeId, updatedTheme);
    }

    @GetMapping("/sorted")
    @ResponseStatus(HttpStatus.OK)
    public List<Theme> getAllThemes( ) {
        return themeService.getAllThemesSortedByCreationDate();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Theme> getThemeById(@PathVariable Long id) {
        Theme theme = themeService.getThemeById(id);
        return ResponseEntity.ok(theme);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        try {
            themeService.deleteThemeById(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/batch")
    public ResponseEntity<Void> deleteMultipleThemes(@RequestParam List<Long> ids) {
        themeService.deleteMultipleThemesByIds(ids);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public List<Theme> searchThemesByKeyword(
            @RequestParam String keyword) {
        return themeService.searchThemesByName(keyword);
    }

    @GetMapping("/count")
    public long countThemes(){
        return themeService.countThemes();
    }
}