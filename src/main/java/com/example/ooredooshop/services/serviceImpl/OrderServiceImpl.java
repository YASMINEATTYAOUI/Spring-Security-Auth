package com.example.ooredooshop.services.serviceImpl;


import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Client;
import com.example.ooredooshop.models.Order;
import com.example.ooredooshop.repositories.OrderRepository;
import com.example.ooredooshop.services.OrderService;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
/*
    @Override
    @Transactional
    public Order toggleOrderStatus(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        // Toggle the active status of the client
        order.setOrderStatus(!order.isVerified());

        return orderRepository.save(order);
    }
*/
    @Override
    @Transactional
    public Order toggleOrderStatus(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));

        Boolean currentStatus = order.getOrderStatus();

        if (currentStatus == null) {
            order.setOrderStatus(true);
        } else {
            order.setOrderStatus(!currentStatus);
        }

        orderRepository.save(order);
        return order;
    }
    @Override
    public Order getOrderById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order with ID " + id + " not found"));
        logger.info("Order {} is fetched", order.getId());

        return order;
    }

    @Override
    public List<Order> getAllOrdersSortedByCreationDate() {
        logger.info("Retrieving All Order (Sorted)");
        return orderRepository.findAllByOrderByCreationDateDesc();
    }

    @Override
    public void deleteOrderById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order not found with ID: " + id));
        orderRepository.delete(order);
        logger.info("Order {} is deleted", order.getId());

    }

    @Override
    public void deleteMultipleOrdersByIds(List<Long> ids) {

        logger.info("Batch deletion of orders with IDs: {}", ids);
        orderRepository.deleteAllById(ids);
    }
    @Override
    public long countOrders() {
        return orderRepository.count();
    }

}

