package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.example.demo.model.Role;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping
    public User createUser(@RequestBody User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/test")
    public String test() {
        return "Working";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {

        if (!userRepository.existsById(id)) {
            return "User not found with id: " + id;
        }

        userRepository.deleteById(id);
        return "User deleted successfully!";
    }

    @DeleteMapping("/cleanup/null-users")
    public String deleteNullUsers() {

        List<User> users = userRepository.findAll();

        int count = 0;

        for (User user : users) {
            if (user.getName() == null && user.getEmail() == null) {
                userRepository.delete(user);
                count++;
            }
        }

        return count + " null users deleted.";
    }
}