package org.example.store.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.store.model.dto.DummyProductDTO;
import org.example.store.service.DummyProductService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/v1/dummy-products")
@RequiredArgsConstructor
@Slf4j
public class DummyProductController {
    private final DummyProductService dummyProductService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<DummyProductDTO> getAllDummyProducts() {
        return dummyProductService.getAllDummyProducts();
    }

    @GetMapping(value = "/batches", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<List<DummyProductDTO>> streamAllDummyProductsBatches() {
        return dummyProductService.getAllDummyProducts()
                .buffer(5)
                .doOnNext(batch -> {
                    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    log.info("Sending Batch Of Size {} At {}", batch.size(), timestamp);
                })
                .delayElements(Duration.ofSeconds(2));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<DummyProductDTO> getDummyProductById(@PathVariable Integer id) {
        return dummyProductService.getDummyProductById(id);
    }
}
