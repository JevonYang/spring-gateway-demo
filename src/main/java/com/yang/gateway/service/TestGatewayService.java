package com.yang.gateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
@Configuration
public class TestGatewayService implements ApplicationEventPublisherAware {

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    @Autowired
    private ApplicationEventPublisher publisher;

    public String save() {
        System.out.println("--------------TestGatewayService Save--------------------");
        RouteDefinition definition = new RouteDefinition();
        PredicateDefinition predicate = new PredicateDefinition();
        Map<String, String> predicateParams = new HashMap<>(8);

        definition.setId("BaiduRoute");
        predicate.setName("Path");
        predicateParams.put("pattern", "/baidu");
        predicateParams.put("pathPattern", "/baidu");
        predicate.setArgs(predicateParams);
        definition.setPredicates(Arrays.asList(predicate));
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8000/hello").build().toUri();
        definition.setUri(uri);
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return "success";
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
