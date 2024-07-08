package com.example.dongmenu.backend.controller;

import com.example.dongmenu.backend.dto.CartDTO;
import com.example.dongmenu.backend.dto.ProductDTO;
import com.example.dongmenu.backend.model.Cart;
import com.example.dongmenu.backend.model.Product;
import com.example.dongmenu.backend.model.User;
import com.example.dongmenu.backend.repository.CartRepository;
import com.example.dongmenu.backend.repository.OrderRepository;
import com.example.dongmenu.backend.repository.ProductRepository;
import com.example.dongmenu.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @GetMapping
    public List<Cart> getAllCart() {
        return cartRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Cart> getOrderById(@PathVariable Long id) {
        return cartRepository.findById(id);
    }

    @PostMapping
    public Cart createOrder(@RequestBody CartDTO cartDTO) {
        User user = userRepository.findById(cartDTO.getUser())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(cartDTO.getProduct())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(cartDTO.getQuantity());

        return cartRepository.save(cart);
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
    public void deleteCart(@PathVariable Long id) {
        cartRepository.deleteById(id);
    }
}
