package com.example.ooredooshop.controller;


import com.example.ooredooshop.exceptions.NotFoundException;
import com.example.ooredooshop.models.Client;
import com.example.ooredooshop.models.Order;
import com.example.ooredooshop.services.OrderService;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    @PutMapping("/{orderId}/toggle")
    public ResponseEntity<Order> toggleOrderStatus(@PathVariable Long orderId) {
        Order updatedOrder = orderService.toggleOrderStatus(orderId);
        return ResponseEntity.ok(updatedOrder);
    }
    @GetMapping("/sorted")
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrders() {

        return orderService.getAllOrdersSortedByCreationDate();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrderById(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/batch")
    public ResponseEntity<Void> deleteMultipleOrders(@RequestParam List<Long> ids) {
        orderService.deleteMultipleOrdersByIds(ids);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count")
    public long countOrders(){
        return orderService.countOrders();
    }
}
