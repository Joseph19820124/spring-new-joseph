package com.example.demo.config;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class DataInitializer {
    
    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);
    
    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return args -> {
            log.info("Initializing sample data...");
            
            User user1 = new User("john_doe", "john@example.com");
            User user2 = new User("jane_smith", "jane@example.com");
            User user3 = new User("bob_wilson", "bob@example.com");
            
            userRepository.saveAll(Arrays.asList(user1, user2, user3));
            
            log.info("Sample data initialized successfully");
            log.info("Running on thread: {}", Thread.currentThread());
        };
    }
}