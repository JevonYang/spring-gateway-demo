package com.yang.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class RouterConfig {

    @Autowired
    private CostumHandler costumHandler;

//    @Bean
//    public RouterFunction<ServerResponse> userRouter() {
//        return RouterFunctions.route(GET("/user"), costumHandler::getUser);
//    }

}
