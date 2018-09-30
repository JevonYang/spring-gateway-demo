package com.yang.gateway.handlers;

import com.yang.gateway.model.User;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class CostumHandler {

    public Mono<ServerResponse> getUser (ServerRequest serverRequest) {
        return ok().contentType(MediaType.TEXT_PLAIN).body(Mono.just(new User("adgadgasdg124e23dvc", "yang", "123456").toString()), String.class);
    }


}
