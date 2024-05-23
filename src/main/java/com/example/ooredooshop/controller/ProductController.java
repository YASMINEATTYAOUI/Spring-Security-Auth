package com.example.ooredooshop.controller;

import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Category;
import com.example.ooredooshop.models.Package;
import com.example.ooredooshop.models.Product;
import com.example.ooredooshop.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Product> createProduct(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("reference") String reference,
                                                 @RequestParam("description") String description,
                                                 @RequestParam("price") Float price,
                                                 @RequestParam("soldQuantity")Integer soldQuantity,
                                                 @RequestParam("availableQuantity")Integer availableQuantity) throws IOException {
        Product product = new Product();
        product.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        product.setReference(reference);
        product.setDescription(description);
        product.setPrice(price);
        product.setSoldQuantity(soldQuantity);
        product.setAvailableQuantity(availableQuantity);
        productService.createProduct(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Product updateProduct(@RequestBody Product updatedProduct) {
        return productService.updateProduct(updatedProduct);
    }

    @GetMapping("/sorted")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts() {
        return productService.getAllProductsSortedByCreationDate();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/batch")
    public ResponseEntity<Void> deleteMultipleProducts(@RequestParam List<Long> ids) {
        productService.deleteMultipleProductsByIds(ids);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public List<Product> searchProductsByKeyword(
            @RequestParam String keyword) {
        return productService.searchProductsByReference(keyword);
    }

    @GetMapping("/category")
    public List<Product> searchProductsByCategory(
            @RequestParam Category category,
            Pageable pageable
    ) {
        return productService.getProductByCategory(category);
    }

    @GetMapping("/category/reference")
    public List<Product> searchProductsByCategoryAndReference(
            @RequestParam Category category, String reference,
            Pageable pageable
    ) {
        return productService.getProductByCategoryAndReference(category,reference);
    }



    @GetMapping("/count")
    public long countProducts(){
        return productService.countProducts();
    }

}
