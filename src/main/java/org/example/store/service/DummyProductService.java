package org.example.store.service;

import lombok.RequiredArgsConstructor;
import org.example.store.exception.DummyProductFetchException;
import org.example.store.model.dto.DummyProductDTO;
import org.example.store.model.dto.DummyProductResponseDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DummyProductService {
    private final WebClient webClient;

    @Cacheable(value = "allDummyProducts")
    public Flux<DummyProductDTO> getAllDummyProducts() {
        return webClient.get()
                .uri("/products")
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> Mono.error(new DummyProductFetchException("Failed To Fetch Products, Status: " + clientResponse.statusCode())))
                .bodyToMono(DummyProductResponseDTO.class)
                .flatMapMany(response -> Flux.fromIterable(response.getProducts()));
    }

    @Cacheable(value = "dummyProductById", key = "#id")
    public Mono<DummyProductDTO> getDummyProductById(Integer id) {
        return webClient.get()
                .uri("/products/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::isError, clientResponse -> Mono.error(new DummyProductFetchException("Failed To Fetch Product With ID '" + id + "', Status: " + clientResponse.statusCode())))
                .bodyToMono(DummyProductDTO.class);
    }
}
