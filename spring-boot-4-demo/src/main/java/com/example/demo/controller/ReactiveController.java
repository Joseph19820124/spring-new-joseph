package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

@RestController
@RequestMapping("/api/reactive")
public class ReactiveController {
    
    private static final Logger log = LoggerFactory.getLogger(ReactiveController.class);
    
    @Autowired
    private RestClient restClient;
    
    @GetMapping("/mono")
    public Mono<String> getMono() {
        return Mono.just("Hello from Mono!")
                .delayElement(Duration.ofSeconds(1))
                .doOnNext(msg -> log.info("Producing: {} on thread: {}", msg, Thread.currentThread().getName()));
    }
    
    @GetMapping(value = "/flux", produces = "text/event-stream")
    public Flux<String> getFlux() {
        return Flux.interval(Duration.ofSeconds(1))
                .take(5)
                .map(i -> "Item " + i)
                .doOnNext(item -> log.info("Producing: {} on thread: {}", item, Thread.currentThread().getName()));
    }
    
    @SuppressWarnings("unchecked")
    @GetMapping("/github/{username}")
    public Mono<Map<String, Object>> getGithubUser(@PathVariable String username) {
        return Mono.fromCallable(() -> {
            log.info("Fetching GitHub user: {} on thread: {}", username, Thread.currentThread().getName());
            try {
                Map<String, Object> result = restClient.get()
                        .uri("/users/{username}", username)
                        .retrieve()
                        .body(Map.class);
                return result;
            } catch (Exception e) {
                log.error("Error fetching GitHub user {}: {}", username, e.getMessage());
                throw e;
            }
        }).onErrorReturn(Map.of(
            "error", "Failed to fetch GitHub user",
            "username", username,
            "message", "可能原因：GitHub API速率限制、用户不存在或网络问题",
            "suggestion", "请稍后重试或检查用户名是否正确"
        ));
    }
    
    @GetMapping("/combined")
    public Flux<Object> getCombined() {
        Mono<String> mono1 = Mono.just("First").delayElement(Duration.ofMillis(100));
        Mono<String> mono2 = Mono.just("Second").delayElement(Duration.ofMillis(200));
        Flux<Integer> flux = Flux.range(1, 3).delayElements(Duration.ofMillis(150));
        
        return Flux.merge(mono1, mono2, flux.cast(Object.class))
                .doOnNext(item -> log.info("Combined item: {} on thread: {}", item, Thread.currentThread().getName()));
    }
    
    @PostMapping("/process")
    public Mono<String> processData(@RequestBody Mono<String> data) {
        return data
                .map(String::toUpperCase)
                .map(s -> "Processed: " + s)
                .doOnNext(result -> log.info("Processing result: {} on thread: {}", result, Thread.currentThread().getName()));
    }
}