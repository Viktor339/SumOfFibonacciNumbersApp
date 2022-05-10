package com.example.client.service;

import com.example.client.controller.request.ServerRequest;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import reactor.cache.CacheMono;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Signal;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final RSocketRequester rSocketRequester;
    private final Cache<Integer, ? super Signal<? extends Long>> cache;

    public Mono<Long> countSumFromRange(ServerRequest serverRequest) {

        return CacheMono.lookup(this.cache.asMap(), serverRequest.hashCode())
                .onCacheMissResume(() -> rSocketRequester.route("getFibonacciNums")
                        .data(serverRequest)
                        .retrieveFlux(Long.class)
                        .reduce(Long::sum));
    }

}
