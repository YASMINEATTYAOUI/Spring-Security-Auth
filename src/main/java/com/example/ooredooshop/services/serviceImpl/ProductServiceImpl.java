package com.example.ooredooshop.services.serviceImpl;

import com.example.ooredooshop.dtos.ProductDto;
import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Brand;
import com.example.ooredooshop.models.Category;
import com.example.ooredooshop.models.Product;
import com.example.ooredooshop.repositories.BrandRepository;
import com.example.ooredooshop.repositories.CategoryRepository;
import com.example.ooredooshop.repositories.ProductRepository;
import com.example.ooredooshop.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public Product createProduct(Product product) {
        product.setCreationDate(new Date());

        logger.info("Product {} is saved", product.getId());
        return productRepository.save(product);
    }

    @Override
public Product createProduct(MultipartFile file, String reference, String description, Float price, Integer soldQuantity, Integer availableQuantity, Long brandId, Long categoryId)  throws IOException  {
    Optional<Brand> brand = brandRepository.findById(brandId);
    Optional<Category> category = categoryRepository.findById(categoryId);

    if (!brand.isPresent() || !category.isPresent()) {
        throw new IllegalArgumentException("Brand or Category not found");
    }

    Product product = new Product();
    product.setReference(reference);
    product.setDescription(description);
    product.setPrice(price);
    product.setSoldQuantity(soldQuantity);
    product.setAvailableQuantity(availableQuantity);
    product.setBrand(brand.get());
    product.setCategory(category.get());
    product.setCreationDate(new Date());
    product.setLastModifiedDate(new Date());

    if (file != null && !file.isEmpty()) {
        product.setImage(Base64.getEncoder().encodeToString(file.getBytes()));

    }

    return productRepository.save(product);
}
    @Override
    public Product updateProduct(Product updatedProduct) {

        Product existingProduct = productRepository.findById(updatedProduct.getId())
             .orElseThrow(() -> new NotFoundException("Product with ID " + updatedProduct.getId() + " not found"));

       Product product = productRepository.save(updatedProduct);
        logger.info("Product {} got updated", updatedProduct.getId());
        updatedProduct.setLastModifiedDate(new Date());
        return product;
    }
    @Transactional
    public Product updateProduct(Long brandId, Product updatedProduct) {
        // Fetch the existing brand
        Product existingProduct = productRepository.findById(brandId)
                .orElseThrow(() -> new NotFoundException("Brand not found"));

        // Update the brand's fields
        existingProduct.setReference(updatedProduct.getReference());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setSoldQuantity(updatedProduct.getSoldQuantity());
        existingProduct.setAvailableQuantity(updatedProduct.getAvailableQuantity());
        existingProduct.setImage(updatedProduct.getImage());
        existingProduct.setLastModifiedDate(new Date());

        // Save the updated brand
        return productRepository.save(existingProduct);
    }

    @Override
    public Product getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product with ID " + id + " not found"));
        logger.info("Product {} is fetched", product.getId());
        return product;
    }

    @Override
    public List<Product> getAllProductsSortedByCreationDate( ) {
        logger.info("Retrieving All Products (Sorted)");
        return productRepository.findAllByOrderByCreationDateDesc();
    }

    @Override
    public List<Product> getProductByCategory(Category category ) {
        return productRepository.findByCategoryOrderByCreationDateDesc(category);
    }

    @Override
    public List<Product> getProductByCategoryAndReference(Category category, String reference ) {
        logger.info("Retrieving All Products By Category And Reference ");
        return productRepository.findByCategoryAndReferenceContainingIgnoreCaseOrderByCreationDateDesc(category,reference);
    }

    @Override
    public List<Product> searchProductsByReference(String keyword ) {
        return productRepository.findByReferenceContainingIgnoreCaseOrderByCreationDateDesc(keyword);
    }

    @Override
    public void deleteProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + id));
        productRepository.delete(product);
        logger.info("Product {} is deleted", product.getId());

    }

    @Override
    public void deleteMultipleProductsByIds(List<Long> ids) {

        logger.info("Batch deletion of products with IDs: {}", ids);
        productRepository.deleteAllById(ids);
    }
    @Override
    public long countProducts() {
        return productRepository.count();
    }



}
