package com.example.dongmenu.backend.controller;

import com.example.dongmenu.backend.dto.ProductDTO;
import com.example.dongmenu.backend.model.Product;
import com.example.dongmenu.backend.model.User;
import com.example.dongmenu.backend.repository.ProductRepository;
import com.example.dongmenu.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Product> getProductById(@PathVariable Long id) {
        return productRepository.findById(id);
    }

    @PostMapping
    public Product createProduct(@RequestBody ProductDTO productDTO) {

        User user = userRepository.findById(productDTO.getUser())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setSeller(user);
        product.setPrice(productDTO.getPrice());
        return productRepository.save(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        // Update other fields
        return productRepository.save(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }
}
