package com.yang.gateway.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBackController {

    @RequestMapping(value = "/fallback")
    public Mono<String> fallBack() {
        return  Mono.just("连接超时，请重试……");
    }
}
