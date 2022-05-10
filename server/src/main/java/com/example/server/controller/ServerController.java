package com.example.server.controller;

import com.example.server.controller.request.ServerRequest;
import com.example.server.service.ServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;

@Controller
@RequiredArgsConstructor

public class ServerController {

    private final ServerService serverService;

    @MessageMapping("getFibonacciNums")
    public Flux<Long> getFibonacciNumbersFromRange(@RequestBody ServerRequest serverRequest) {

        return serverService.getFibonacciNumbersFromRange(serverRequest);
    }
}
