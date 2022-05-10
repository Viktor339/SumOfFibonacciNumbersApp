package com.example.client.controller;

import com.example.client.controller.request.ServerRequest;
import com.example.client.service.ClientService;
import com.example.client.service.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fibonacci/v1/numbers")
public class ClientController {

    private final DataService dataService;
    private final ClientService clientService;

    @GetMapping(value = "/count", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<Long> countSumFromRange(@RequestParam(name = "from") Integer from,
                                        @RequestParam(name = "to") Integer to) {

        dataService.validateDate(from, to);
        return clientService.countSumFromRange(ServerRequest.builder()
                .from(from)
                .to(to)
                .build());
    }

}

