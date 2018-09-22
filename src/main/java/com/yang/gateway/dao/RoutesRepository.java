package com.yang.gateway.dao;

import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface RoutesRepository extends ReactiveMongoRepository<RouteDefinition, String> {

    public Mono<Long> deleteRouteDefinitionById(String id);
}
