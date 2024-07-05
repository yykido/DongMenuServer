package com.example.dongmenu.backend.repository;

import com.example.dongmenu.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
