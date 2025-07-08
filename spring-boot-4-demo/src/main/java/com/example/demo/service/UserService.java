package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {
    
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    
    @Autowired
    private UserRepository userRepository;
    
    public List<User> findAll() {
        log.debug("Finding all users in thread: {}", Thread.currentThread());
        return userRepository.findAll();
    }
    
    public Optional<User> findById(Long id) {
        log.debug("Finding user by id: {} in thread: {}", id, Thread.currentThread());
        return userRepository.findById(id);
    }
    
    public User save(User user) {
        log.debug("Saving user: {} in thread: {}", user.getUsername(), Thread.currentThread());
        return userRepository.save(user);
    }
    
    @Async
    public CompletableFuture<User> findByIdAsync(Long id) {
        log.debug("Finding user async by id: {} in thread: {}", id, Thread.currentThread());
        return CompletableFuture.supplyAsync(() -> 
            userRepository.findById(id).orElse(null)
        );
    }
    
    public Mono<User> findByIdReactive(Long id) {
        log.debug("Finding user reactive by id: {} in thread: {}", id, Thread.currentThread());
        return Mono.fromCallable(() -> 
            userRepository.findById(id).orElse(null)
        );
    }
    
    public void deleteById(Long id) {
        log.debug("Deleting user by id: {} in thread: {}", id, Thread.currentThread());
        userRepository.deleteById(id);
    }
}