package com.example.ooredooshop.services;

import com.example.ooredooshop.models.Category;
import com.example.ooredooshop.models.Discount;

import java.util.Date;
import java.util.List;

public interface DiscountService {
    void createDiscount(Discount discountRequest);
    Discount updateDiscount(Discount discount);
    Discount getDiscountById(Long id);
    List<Discount> getAllDiscountsSortedByCreationDate( );

    List<Discount> getDiscountsByCategory(Category category );

    List<Discount> getDiscountsByCategoryAndCreationDate(Category category, Date creationDate);
    List<Discount> searchDiscountsByCreationDate(Date creationDate);

    void deleteDiscountById(Long id);
    void deleteMultipleDiscountsByIds(List<Long> ids);

    long countDiscounts();

}
