package com.example.client.service;

import com.example.client.controller.request.ServerRequest;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final RSocketRequester rSocketRequester;
    private final Cache<Integer, Mono<Long>> cache;

    public Mono<Long> countSumFromRange(ServerRequest serverRequest) {

        Mono<Long> sumFromCache = cache.getIfPresent(serverRequest.hashCode());

        if (sumFromCache == null) {

            Mono<Long> sum = getFibonacciNums(serverRequest)
                    .reduce(Long::sum);

            cache.put(serverRequest.hashCode(), sum);

            return sum;
        }
        return sumFromCache;
    }

    public Flux<Long> getFibonacciNums(ServerRequest serverRequest) {
        return rSocketRequester.route("getFibonacciNums")
                .data(serverRequest)
                .retrieveFlux(Long.class);
    }

}
