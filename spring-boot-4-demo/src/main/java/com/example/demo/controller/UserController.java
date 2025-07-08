package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }
    
    @GetMapping("/async/{id}")
    public CompletableFuture<User> getUserAsync(@PathVariable Long id) {
        return userService.findByIdAsync(id);
    }
    
    @GetMapping(value = "/stream", produces = "text/event-stream")
    public Flux<User> streamUsers() {
        return Flux.interval(Duration.ofSeconds(1))
                .take(100)
                .map(i -> new User(i, "joseph" + i, "joseph.siyi" + i + "@gmail.com", LocalDateTime.now()));
    }
    
    @GetMapping("/reactive/{id}")
    public Mono<User> getUserReactive(@PathVariable Long id) {
        return userService.findByIdReactive(id);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }
}