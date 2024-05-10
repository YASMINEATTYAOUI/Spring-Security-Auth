package com.example.ooredooshop.services;


import com.example.ooredooshop.models.Order;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {


    Order getOrderById(Long id);
    List<Order> getAllOrdersSortedByCreationDate();
    List<Order> getAllOrdersByCreatorIdSortedByCreationDate(Long creatorId);

    void deleteOrderById(Long id);
    void deleteMultipleOrdersByIds(List<Long> ids);

    long countOrders();
}
