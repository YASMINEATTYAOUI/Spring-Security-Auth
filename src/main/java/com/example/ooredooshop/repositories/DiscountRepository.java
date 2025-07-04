package com.example.ooredooshop.repositories;


import com.example.ooredooshop.models.Discount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount,Long> {
    List<Discount> findAllByOrderByCreationDateDesc();
    List<Discount> findByCreationDate(Date creationDate);

}
