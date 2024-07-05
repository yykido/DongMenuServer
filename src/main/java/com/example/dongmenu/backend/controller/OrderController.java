package com.example.dongmenu.backend.controller;

import com.example.dongmenu.backend.model.Order;
import com.example.dongmenu.backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Order> getOrderById(@PathVariable Long id) {
        return orderRepository.findById(id);
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }

//    @PutMapping("/{id}")
//    public Order updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
//        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
//        order.setQuantity(orderDetails.getQuantity());
//        order.setOrderDate(orderDetails.getOrderDate());
//        // Update other fields
//        return orderRepository.save(order);
//    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
    }
}
