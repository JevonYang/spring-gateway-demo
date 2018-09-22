package com.yang.gateway.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@ConditionalOnProperty(
        name = {"spring.cloud.gateway.datasource"},
        havingValue = "mongo",
        matchIfMissing = false
)
public class MongoRouteDefinitionRepository implements RouteDefinitionRepository {

    @Autowired
    private RoutesRepository routesRepository;

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return routesRepository.findAll();
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return route.flatMap(
                routeDefinition -> {
                    routesRepository.save(routeDefinition);
                    return Mono.empty();
                });
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return routeId.flatMap(id -> {
            routesRepository.deleteRouteDefinitionById(id);
            return Mono.defer(() -> Mono.error(new NotFoundException("RouteDefinition not found: " + routeId)));
        });
    }
}
