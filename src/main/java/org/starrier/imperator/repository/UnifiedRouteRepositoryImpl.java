package org.starrier.imperator.repository;


import com.google.common.collect.Maps;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Collections.synchronizedMap;

/**
 * @author Starrier
 * @date 2019/4/14
 */

public class UnifiedRouteRepositoryImpl implements  RouteDefinitionRepository {

    private final Map<String, RouteDefinition> routes = synchronizedMap(Maps.newLinkedHashMap());

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        return null;
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return null;
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return null;
    }





}