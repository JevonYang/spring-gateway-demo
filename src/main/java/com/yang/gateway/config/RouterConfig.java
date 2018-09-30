package com.yang.gateway.config;

import com.yang.gateway.handlers.CostumHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterConfig {

    @Autowired
    private CostumHandler costumHandler;

//    @Bean
//    public RouterFunction<ServerResponse> userRouter() {
//        return RouterFunctions.route(GET("/user"), costumHandler::getUser);
//    }

}
