package com.yang.gateway.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@RestController
public class DemoController {

    @RequestMapping(value = "/hello")
    public Mono<String> hello() {
        return Mono.just("hello, reactive world!");
    }


}
