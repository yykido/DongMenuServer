package com.example.dongmenu.backend.repository;

import com.example.dongmenu.backend.model.Order;
import com.example.dongmenu.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUserId(Long id);
}
