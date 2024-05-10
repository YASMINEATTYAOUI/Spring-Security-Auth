package com.example.ooredooshop.repositories;


import com.example.ooredooshop.models.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository <Order,Long> {
    List<Order> findAllByOrderByCreationDateDesc();
    List<Order> findByCreatorIdOrderByCreationDate(Long creatorId);
}
