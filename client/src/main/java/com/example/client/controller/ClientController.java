package com.example.client.controller;

import com.example.client.controller.request.ServerRequest;
import com.example.client.service.ClientService;
import com.example.client.service.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fibonacci/v1/numbers")
@Validated
public class ClientController {

    private final ClientService clientService;

    @GetMapping(value = "/count", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Mono<Long> countSumFromRange(@RequestParam(name = "from") @Min(1) @Max(49) Integer from,
                                        @RequestParam(name = "to") @Min(2) @Max(50) Integer to) {

        if (from >= to) {
            throw new ValidationException("From must be less then to");
        }
        return clientService.countSumFromRange(ServerRequest.builder()
                .from(from)
                .to(to)
                .build());
    }

}

