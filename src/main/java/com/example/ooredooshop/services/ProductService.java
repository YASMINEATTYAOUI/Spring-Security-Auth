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
    List<Product> getAllProductsSortedByCreationDate( );
    List<Product> getAllProductsByCreatorIdSortedByCreationDate(Long creatorId, String reference);

    List<Product> getProductByCategory(Category category);

    List<Product> getProductByCategoryAndReference(Category category, String reference );
    List<Product> searchProductsByReference(String keyword);

    void deleteProductById(Long id);
    void deleteMultipleProductsByIds(List<Long> ids);

    long countProducts();

    long countByTagsContainingIgnoreCase(String tag);

}
