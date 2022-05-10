package com.example.client.e2e;

import com.example.client.controller.ClientController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.stream.Stream;

@ExtendWith(ServerExtension.class)
@ContextConfiguration(
        classes = {
                com.example.client.ClientApplication.class,
                com.example.client.config.ClientConfiguration.class,
                com.example.client.config.CacheConfig.class,
                com.example.client.config.CacheProperties.class,
                com.example.client.service.DataService.class,
                com.example.client.config.ClientProperties.class,
                com.example.client.service.ClientService.class
        })
@WebFluxTest(value = ClientController.class)
public class EndToEndTest {

    @Autowired
    private WebTestClient testClient;

    @BeforeEach
    public void setUp() {
        testClient = testClient.mutate()
                .responseTimeout(Duration.ofMillis(30000))
                .build();
    }

    @Test
    public void testGetShouldReturnSumFoNumbers() {

        Long expect = 15557484098L;
        Integer from = 48;
        Integer to = 50;

        Flux<Long> responseBody = testClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/fibonacci/v1/numbers/count/")
                        .queryParam("from", from)
                        .queryParam("to", to)
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Long.class)
                .getResponseBody();

        StepVerifier.create(responseBody)
                .expectSubscription()
                .expectNext(expect)
                .verifyComplete();

    }


    @ParameterizedTest
    @MethodSource("argumentsStream")
    public void testGetShouldThrowException(Integer from, Integer to) {

        testClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/fibonacci/v1/numbers/count/")
                        .queryParam("from", from)
                        .queryParam("to", to)
                        .build())
                .exchange()
                .expectStatus()
                .is4xxClientError();

    }

    private static Stream<Arguments> argumentsStream() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(null, 1),
                Arguments.of(1, null),
                Arguments.of(0, 1),
                Arguments.of(5, 51),
                Arguments.of(22, 21));
    }

}
