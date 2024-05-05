package com.example.ooredooshop.services;

import com.example.ooredooshop.models.Category;
import com.example.ooredooshop.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    void createProduct(Product productRequest);
    Product updateProduct(Product product);

    Product getProductById(Long id);
    Page<Product> getAllProductsSortedByCreationDate(Pageable pageable);
    Page<Product> getAllProductsByCreatorIdSortedByCreationDate(Long creatorId, String reference, Pageable pageable);

    Page<Product> getProductByCategory(Category category, Pageable pageable);

    Page<Product> getProductByCategoryAndReference(Category category, String reference, Pageable pageable);
    Page<Product> searchProductsByReference(String keyword, Pageable pageable);

    void deleteProductById(Long id);
    void deleteMultipleProductsByIds(List<Long> ids);

    long countProducts();

    long countByTagsContainingIgnoreCase(String tag);

}
