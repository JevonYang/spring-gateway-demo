package com.yang.gateway;

import com.alibaba.fastjson.JSON;
import com.yang.gateway.dao.RoutesRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GatewayApplicationTests   {

//    @Autowired
//    private StringRedisTemplate redisTemplate;

    @Autowired
    private RoutesRepository routesRepository;

    public static final String GATEWAY_ROUTES = "geteway_routes";



    @Test
    public void contextLoads() throws InterruptedException {
        RouteDefinition definition = new RouteDefinition();
        PredicateDefinition predicate = new PredicateDefinition();
        Map<String, String> predicateParams = new HashMap<>(8);

        definition.setId("helloRoute");
        predicate.setName("Path");
        predicateParams.put("pattern", "/hello");
        predicateParams.put("pathPattern", "/hello");
        predicate.setArgs(predicateParams);
        definition.setPredicates(Arrays.asList(predicate));
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8000/hello").build().toUri();
        definition.setUri(uri);

        Mono<RouteDefinition> mono = routesRepository.save(definition);
        mono.flatMap(
                routeDefinition -> {
                    System.out.println(routeDefinition.getUri());
                    return Mono.empty();
                }
        );
        Flux<RouteDefinition> flux = routesRepository.findAll();
        flux.flatMap(
                routeDefinition -> {
                    System.out.println("----------RouteDefinition-----------");
                    System.out.println(routeDefinition.getId());
                    return Mono.empty();
                }
        );
    }

}
