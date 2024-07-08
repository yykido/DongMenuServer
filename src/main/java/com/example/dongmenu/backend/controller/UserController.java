package com.example.dongmenu.backend.controller;

import com.example.dongmenu.backend.dto.LoginDTO;
import com.example.dongmenu.backend.model.Product;
import com.example.dongmenu.backend.model.User;
import com.example.dongmenu.backend.repository.OrderRepository;
import com.example.dongmenu.backend.repository.ProductRepository;
import com.example.dongmenu.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/login")
    public ResponseEntity<Map<String, Object>> getOrderDetails(@RequestParam String name, @RequestParam String password) {
        // find user in database
        Optional<User> userOptional = userRepository.findByName(name);

        // check if name and password is matched
        if (!userOptional.isPresent() || !userOptional.get().getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // name or password incorrect
        }

        User user = userOptional.get();
        System.out.println("user found");
        System.out.println(user.getName());
        System.out.println(user.getPassword());

        // get order according to user's id
//        Optional<Order> orderOptional = orderRepository.findByUserId(user.getId());
//        if (!orderOptional.isPresent()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // can not find order
//        }

//        Order order = orderOptional.get();

        // get all product information
        List<Product> products = productRepository.findAll();

        // construct response
        Map<String, Object> response = new HashMap<>();
//        response.put("order", order);
        response.put("user", user);
        response.put("products", products);

        return ResponseEntity.ok(response); // 返回包含订单、用户和产品信息的Map
    }

    // need to update
    // login part
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginDTO loginDTO) {
        Optional<User> userOptional = userRepository.findByName(loginDTO.getUsername());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(loginDTO.getPassword())) {
                return ResponseEntity.ok(user); // Password correct, return user data
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // Password incorrect
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // User not found
        }
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(userDetails.getName());
        // Update other fields
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}