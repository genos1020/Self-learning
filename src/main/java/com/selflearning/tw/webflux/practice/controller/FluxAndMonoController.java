package com.selflearning.tw.webflux.practice.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("practice")
public class FluxAndMonoController {

    @GetMapping("/flux")
    public Flux<Integer> getIntegersFlux() {
        return Flux.just(1, 2, 3, 4)
                .delayElements(Duration.ofSeconds(1))
                .log();
    }

    @GetMapping(value = "/fluxStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Integer> getIntegersFluxStream() {
        return Flux.just(1, 2, 3, 4)
                .delayElements(Duration.ofSeconds(1))
                .log();
    }

    @GetMapping("/mono")
    public Mono<Integer> getIntegerMono(){
        return Mono.just(9)
                .delayElement(Duration.ofSeconds(3))
                .log();
    }
}
