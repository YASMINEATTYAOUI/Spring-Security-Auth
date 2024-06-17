package com.example.ooredooshop.services;

import com.example.ooredooshop.models.Category;
import com.example.ooredooshop.models.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    Product createProduct(MultipartFile file, String reference, String description, Float price, Integer soldQuantity, Integer availableQuantity, Long brandId, Long categoryId) throws IOException;
    Product createProduct(Product productRequest);
    Product updateProduct(Product product);
    Product updateProduct(Long brandId, Product updatedProduct);
    Product getProductById(Long id);
    List<Product> getAllProductsSortedByCreationDate( );

    List<Product> getProductByCategory(Category category);

    List<Product> getProductByCategoryAndReference(Category category, String reference );
    List<Product> searchProductsByReference(String keyword);

    void deleteProductById(Long id);
    void deleteMultipleProductsByIds(List<Long> ids);

    long countProducts();


}
