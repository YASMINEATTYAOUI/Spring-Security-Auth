package com.example.ooredooshop.services.serviceImpl;


import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Order;
import com.example.ooredooshop.repositories.OrderRepository;
import com.example.ooredooshop.services.OrderService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public Order getOrderById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order with ID " + id + " not found"));
        // log.info("Order {} is fetched", order.getId());

        return order;
    }

    @Override
    public List<Order> getAllOrdersSortedByCreationDate() {
        // log.info("Retrieving All Order (Sorted)");
        return orderRepository.findAllByOrderByCreationDateDesc();
    }

    @Override
    public List<Order> getAllOrdersByCreatorIdSortedByCreationDate(Long creatorId) {
        return orderRepository.findByCreatorIdOrderByCreationDate(creatorId);
    }

    @Override
    public void deleteOrderById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with ID: " + id));
        orderRepository.delete(order);
        //log.info("Order {} is deleted", order.getId());

    }

    @Override
    public void deleteMultipleOrdersByIds(List<Long> ids) {

        // log.info("Batch deletion of orders with IDs: {}", ids);
        orderRepository.deleteAllById(ids);
    }
    @Override
    public long countOrders() {
        return orderRepository.count();
    }

}

