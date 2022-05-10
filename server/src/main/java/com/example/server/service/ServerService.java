package com.example.server.service;

import com.example.server.controller.request.ServerRequest;
import lombok.Getter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Getter
public class ServerService {

    private final List<Long> fibonacciNumbersList = Stream.iterate(new long[]{0, 1}, t -> new long[]{t[1], t[0] + t[1]})
            .limit(50)
            .map(t -> t[0])
            .collect(Collectors.toList());

    public Flux<Long> getFibonacciNumbersFromRange(ServerRequest serverRequest) {

        Integer from = serverRequest.getFrom();
        Integer to = serverRequest.getTo();

        return Flux
                .fromStream(fibonacciNumbersList.stream().skip(from - 1).limit(to - from + 1))
                .delayElements(Duration.ofMillis(100));
    }

}
