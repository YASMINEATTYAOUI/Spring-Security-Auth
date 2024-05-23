package com.example.ooredooshop.controller;

import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Color;
import com.example.ooredooshop.services.ColorService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
@RequestMapping("/api/colors")
public class ColorController {
    private final ColorService colorService;

    @PostMapping // Updated method
    @ResponseStatus(HttpStatus.CREATED)
    public void createColor(@RequestBody Color color) {
        colorService.createColor(color);
    }

    @PutMapping // Updated method
    @ResponseStatus(HttpStatus.OK)
    public Color updateColor(@RequestBody Color updatedColor) {
        return colorService.updateColor(updatedColor);
    }

    @GetMapping("/sorted")
    @ResponseStatus(HttpStatus.OK)
    public List<Color> getAllColors() {
        return colorService.getAllColorsSortedByCreationDate();
    }

    @GetMapping("/{id}") // Updated endpoint path variable
    public ResponseEntity<Color> getColorById(@PathVariable Long id) {
        Color color = colorService.getColorById(id);
        return ResponseEntity.ok(color);
    }

    @DeleteMapping("/{id}") // Updated endpoint path variable
    public ResponseEntity<Void> deleteColor(@PathVariable Long id) {
        try {
            colorService.deleteColorById(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/batch")
    public ResponseEntity<Void> deleteMultipleColors(@RequestParam List<Long> ids) {
        colorService.deleteMultipleColorsByIds(ids);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public List<Color> searchColorsByKeyword( @RequestParam String keyword) {
        return colorService.searchColorsByName(keyword);
    }



    @GetMapping("/count")
    public long countColors(){
        return colorService.countColors();
    }

}
