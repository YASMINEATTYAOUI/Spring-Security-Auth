package com.example.ooredooshop.controller;

import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Category;
import com.example.ooredooshop.models.Discount;
import com.example.ooredooshop.services.DiscountService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
@RequestMapping("/api/discounts")
public class DiscountController {

    private final DiscountService discountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createDiscount(@RequestBody Discount discount) {
        discountService.createDiscount(discount);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Discount updateDiscount(@RequestBody Discount updatedDiscount) {
        return discountService.updateDiscount(updatedDiscount);
    }

    @GetMapping("/sorted")
    @ResponseStatus(HttpStatus.OK)
    public List<Discount> getAllDiscounts() {
        return discountService.getAllDiscountsSortedByCreationDate();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Discount> getDiscountById(@PathVariable Long id) {
        Discount product = discountService.getDiscountById(id);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Long id) {
        try {
            discountService.deleteDiscountById(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/batch")
    public ResponseEntity<Void> deleteMultipleDiscounts(@RequestParam List<Long> ids) {
        discountService.deleteMultipleDiscountsByIds(ids);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public List<Discount> searchDiscountsByCreationDate(
            @RequestParam Date creationDate
    ) {
        return discountService.searchDiscountsByCreationDate(creationDate);
    }

    @GetMapping("/category")
    public List<Discount> searchDiscountsByCategory(
            @RequestParam Category category
    ) {
        return discountService.getDiscountsByCategory(category);
    }

    @GetMapping("/category/reference")
    public List<Discount> searchDiscountsByCategoryAndReference(
            @RequestParam Category category, Date creationDate
    ) {
        return discountService.getDiscountsByCategoryAndCreationDate(category, creationDate);
    }




    @GetMapping("/count")
    public long countDiscounts() {
        return discountService.countDiscounts();
    }
}
