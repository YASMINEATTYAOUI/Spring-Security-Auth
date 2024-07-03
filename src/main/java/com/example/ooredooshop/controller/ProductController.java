package com.example.ooredooshop.controller;

import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Brand;
import com.example.ooredooshop.models.Category;
import com.example.ooredooshop.models.Product;
import com.example.ooredooshop.repositories.BrandRepository;
import com.example.ooredooshop.repositories.CategoryRepository;
import com.example.ooredooshop.repositories.ProductRepository;
import com.example.ooredooshop.services.ProductService;
import lombok.AllArgsConstructor;
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
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestParam("file") MultipartFile file,
                                 @RequestParam("reference") String reference,
                                 @RequestParam("description") String description,
                                 @RequestParam("price") Float price,
                                 @RequestParam("soldQuantity") Integer soldQuantity,
                                 @RequestParam("availableQuantity") Integer availableQuantity,
                                 @RequestParam("brand") Long brandId,
                                 @RequestParam("category") Long categoryId
                                 ) throws IOException {
        Product newProduct = new Product();
        newProduct.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        newProduct.setReference(reference);
        newProduct.setDescription(description);
        newProduct.setPrice(price);
        newProduct.setSoldQuantity(soldQuantity);
        newProduct.setAvailableQuantity(availableQuantity);
        Brand brand = brandRepository.findById(brandId).orElseThrow(() -> new RuntimeException("Brand not found"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));


        newProduct.setBrand(brand);
        newProduct.setCategory(category);
        return productService.createProduct(newProduct);
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public Product updateProduct(@PathVariable Long productId,
                                 @RequestParam(value = "file", required = false) MultipartFile file,
                                 @RequestParam("reference") String reference,
                                 @RequestParam("description") String description,
                                 @RequestParam("price") Float price,
                                 @RequestParam("soldQuantity") Integer soldQuantity,
                                 @RequestParam("availableQuantity") Integer availableQuantity,
                                 @RequestParam("brand") Long brandId,
                                 @RequestParam("category") Long categoryId) throws IOException {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (file != null) {
            existingProduct.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        }
        existingProduct.setReference(reference);
        existingProduct.setDescription(description);
        existingProduct.setPrice(price);
        existingProduct.setSoldQuantity(soldQuantity);
        existingProduct.setAvailableQuantity(availableQuantity);

        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existingProduct.setBrand(brand);
        existingProduct.setCategory(category);

        return productService.updateProduct(existingProduct);
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
