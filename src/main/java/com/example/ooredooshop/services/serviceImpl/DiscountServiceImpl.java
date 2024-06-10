package com.example.ooredooshop.services.serviceImpl;


import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Category;
import com.example.ooredooshop.models.Discount;
import com.example.ooredooshop.repositories.DiscountRepository;
import com.example.ooredooshop.services.DiscountService;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private static final Logger logger = LoggerFactory.getLogger(DiscountServiceImpl.class);

    @Override
    public void createDiscount(Discount discount) {
        discount.setCreationDate(new Date());
        discount.setLastModifiedDate(new Date());
        discountRepository.save(discount);
        logger.info("Discount {} is saved", discount.getId());
    }

    @Override
    public Discount updateDiscount(Discount updatedDiscount) {

        Discount finalUpdatedDiscount = updatedDiscount;
        Discount existingDiscount = discountRepository.findById(updatedDiscount.getId())
         .orElseThrow(() -> new NotFoundException("Discount with ID " + finalUpdatedDiscount.getId() + " not found"));

         updatedDiscount = discountRepository.save(existingDiscount);
         logger.info("Discount {} got updated", updatedDiscount.getId());

        return updatedDiscount;
    }

    @Override
    public Discount getDiscountById(Long id) {

        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Discount with ID " + id + " not found"));
        logger.info("Discount {} is fetched", discount.getId());

        return discount;
    }

    @Override
    public List<Discount> getAllDiscountsSortedByCreationDate() {
        logger.info("Retrieving All Discounts (Sorted)");
        return discountRepository.findAllByOrderByCreationDateDesc();
    }



    @Override
    public List<Discount> getDiscountsByCategory(Category category) {
        return null; //discountRepository.findAllByCategoryOrderByCreationDateDesc(category);
    }

    @Override
    public List<Discount> getDiscountsByCategoryAndCreationDate(Category category, Date creationDate ) {
        logger.info("Retrieving All Discounts By Category And Name ");
        return null; //discountRepository.findAllByCategoryAndNameContainingIgnoreCaseOrderByCreationDateDesc(category,name);
    }

    @Override
    public List<Discount> searchDiscountsByCreationDate(Date creationDate ) {
        return discountRepository.findByCreationDate(creationDate);
    }

    @Override
    public void deleteDiscountById(Long id) {

        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Discount not found with ID: " + id));
        discountRepository.delete(discount);
        logger.info("Discount {} is deleted", discount.getId());

    }

    @Override
    public void deleteMultipleDiscountsByIds(List<Long> ids) {

        logger.info("Batch deletion of discounts with IDs: {}", ids);
        discountRepository.deleteAllById(ids);
    }
    @Override
    public long countDiscounts() {
        return discountRepository.count();
    }
}
