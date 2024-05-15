package com.example.ooredooshop.services.serviceImpl;

import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Category;
import com.example.ooredooshop.models.Product;
import com.example.ooredooshop.repositories.ProductRepository;
import com.example.ooredooshop.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Override
    public void createProduct(Product product) {

        productRepository.save(product);
        logger.info("Product {} is saved", product.getId());

    }

    @Override
    public Product updateProduct(Product updatedProduct) {

        Product existingProduct = productRepository.findById(updatedProduct.getId())
             .orElseThrow(() -> new NotFoundException("Product with ID " + updatedProduct.getId() + " not found"));

       Product product = productRepository.save(updatedProduct);
        logger.info("Product {} got updated", updatedProduct.getId());

        return product;
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
    public List<Product>  getAllProductsByCreatorIdSortedByCreationDate(Long creatorId, String reference ) {

        if(reference != null){
            return productRepository.findByCreatorIdAndReferenceContainingIgnoreCaseOrderByCreationDate(creatorId, reference);
        }
        return productRepository.findByCreatorIdOrderByCreationDate(creatorId);
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

    @Override
    public long countByTagsContainingIgnoreCase(String tag) {
        return this.productRepository.countByTagsContainingIgnoreCase(tag);
    }

}
