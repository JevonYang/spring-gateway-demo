package com.yang.gateway;

import com.yang.gateway.dao.RoutesRepository;
import com.yang.gateway.service.LuaRateLimiterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GatewayApplicationTests   {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RoutesRepository routesRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private LuaRateLimiterService luaRateLimiterService;


    public static final String GATEWAY_ROUTES = "geteway_routes";


    @Test
    public void redisTest() {
        redisTemplate.opsForValue().setIfAbsent("lock:hole", "hello");
        redisTemplate.opsForValue().getOperations().expire("lock:hole", 5, TimeUnit.MINUTES);

    }

    @Test
    public void luaRedisTest() {
//        String RateLimiter
//        luaRateLimiterService.initRateLimiter("hello", "100", "100");
        Long timeNow = System.currentTimeMillis();
        for (int i = 0; i <1000 ; i++) {
            System.out.println(luaRateLimiterService.acquire("RateLimiter:test", "100", "100", "1", timeNow.toString()));
        }
    }

    @Test
    public void redisLockTest() {

    }

    @Test
    public void contextLoads() throws InterruptedException {
        RouteDefinition definition = new RouteDefinition();
        PredicateDefinition predicate = new PredicateDefinition();
        Map<String, String> predicateParams = new HashMap<>(8);

        definition.setId("helloRoute");
        predicate.setName("Path");
        predicateParams.put("pattern", "/hello/**");
        //predicateParams.put("pathPattern", "/hello1");
        predicate.setArgs(predicateParams);
        definition.setPredicates(Arrays.asList(predicate));
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8000/hello").build().toUri();
        definition.setUri(uri);

        List<FilterDefinition> filterDefinitions = new ArrayList<>();

        FilterDefinition fd1 = new FilterDefinition();
        fd1.setName("Hystrix");
        LinkedHashMap<String, String> fd1Args = new LinkedHashMap<>();
        fd1Args.put("name", "HellFallBack");
        fd1Args.put("fallbackUri", "forward:/fallback");
        fd1.setArgs(fd1Args);
        filterDefinitions.add(fd1);

        FilterDefinition fd2 = new FilterDefinition();
        fd2.setName("RewritePath");
        Map<String, String> fd2Args = new LinkedHashMap<>();

        fd2Args.put("regexp", "/employee/(?<path>.*)");
        fd2Args.put("replacement","/$\\{path}");
        fd2.setArgs(fd2Args);
        filterDefinitions.add(fd2);
        definition.setFilters(filterDefinitions);

        mongoTemplate.save(definition);

//        Mono<RouteDefinition> mono = routesRepository.save(definition);
//        mono.flatMap(
//                routeDefinition -> {
//                    System.out.println(routeDefinition.getUri());
//                    return Mono.empty();
//                }
//        );
//        Flux<RouteDefinition> flux = routesRepository.findAll();
//        flux.flatMap(
//                routeDefinition -> {
//                    System.out.println("----------RouteDefinition-----------");
//                    System.out.println(routeDefinition.getId());
//                    return Mono.empty();
//                }
//        );
    }

}
