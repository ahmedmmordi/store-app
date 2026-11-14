package org.example.store.service;

import org.example.store.exception.DummyProductFetchException;
import org.example.store.model.dto.DummyProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class DummyProductServiceTest {

    @Test
    void searchDummyProductsReturnsMatchingProductsAndEncodesQuery() {
        AtomicReference<String> requestedUri = new AtomicReference<>();
        WebClient webClient = WebClient.builder().exchangeFunction(request -> {
            requestedUri.set(request.url().toString());
            return Mono.just(ClientResponse.create(HttpStatus.OK).header("Content-Type", "application/json").body("""
                    {
                      "products": [
                        {"id": 1, "title": "Phone"},
                        {"id": 2, "title": "Phone Case"}
                      ],
                      "total": 2,
                      "skip": 0,
                      "limit": 30
                    }
                    """).build());
        }).build();
        DummyProductService service = new DummyProductService(webClient);

        StepVerifier.create(service.searchDummyProducts("phone case")).assertNext(product -> {
            assertEquals(1, product.getId());
            assertEquals("Phone", product.getTitle());
        }).assertNext(product -> {
            assertEquals(2, product.getId());
            assertEquals("Phone Case", product.getTitle());
        }).verifyComplete();

        java.net.URI uri = java.net.URI.create(requestedUri.get());
        assertEquals("/products/search", uri.getRawPath());
        assertEquals("phone case",
                java.net.URLDecoder.decode(uri.getRawQuery().substring(2), java.nio.charset.StandardCharsets.UTF_8));
    }

    @Test
    void searchDummyProductsReturnsEmptyFluxWhenNoProductsMatch() {
        WebClient webClient = WebClient.builder().exchangeFunction(request -> Mono
                .just(ClientResponse.create(HttpStatus.OK).header("Content-Type", "application/json").body("""
                        {"products": [], "total": 0, "skip": 0, "limit": 30}
                        """).build())).build();
        DummyProductService service = new DummyProductService(webClient);

        StepVerifier.create(service.searchDummyProducts("not-found")).verifyComplete();
    }

    @Test
    void searchDummyProductsPropagatesExternalApiErrors() {
        WebClient webClient = WebClient.builder()
                .exchangeFunction(request -> Mono.just(ClientResponse.create(HttpStatus.INTERNAL_SERVER_ERROR).build()))
                .build();
        DummyProductService service = new DummyProductService(webClient);

        StepVerifier.create(service.searchDummyProducts("phone")).expectErrorSatisfies(error -> {
            DummyProductFetchException exception = assertInstanceOf(DummyProductFetchException.class, error);
            assertEquals("Failed To Search Products, Status: 500 INTERNAL_SERVER_ERROR", exception.getMessage());
        }).verify();
    }
}
