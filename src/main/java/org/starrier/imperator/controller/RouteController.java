package org.starrier.imperator.controller;


import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.starrier.imperator.model.GatewayPredicateDefinition;
import org.starrier.imperator.model.GatewayRouteDefinition;
import org.starrier.imperator.route.RouteRepresentApplication;

import java.net.URI;
import java.util.List;


/**
 * @author Starrier
 * @date 2018/12/16
 */
@RestController
@RequestMapping("/api/v1/router")
public class RouteController {

    private final RouteRepresentApplication dynamicRouteService;

    public RouteController(RouteRepresentApplication dynamicRouteService) {
        this.dynamicRouteService = dynamicRouteService;
    }

    @GetMapping
    public String findAll() {
        return null;
    }

    /**
     * Add router.
     *
     * @param routeDefinition {@link GatewayPredicateDefinition}
     * @return String.
     */
    @SneakyThrows(Exception.class)
    @PostMapping
    public String add(@RequestBody GatewayRouteDefinition routeDefinition) {
        RouteDefinition definition = assembleRouteDefinition(routeDefinition);
        return this.dynamicRouteService.add(definition);

    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") String id) {
        return this.dynamicRouteService.delete(id);
    }

    /**
     * Update router.
     *
     * @param routeDefinition {@link GatewayPredicateDefinition}
     * @return String.
     */
    @PutMapping
    public String update(@RequestBody GatewayRouteDefinition routeDefinition) {
        RouteDefinition definition = assembleRouteDefinition(routeDefinition);
        return this.dynamicRouteService.update(definition);
    }

    private RouteDefinition assembleRouteDefinition(GatewayRouteDefinition routeDefinition) {
        RouteDefinition definition = new RouteDefinition();
        List<PredicateDefinition> predicateDefinitionList = Lists.newArrayListWithExpectedSize(routeDefinition.getPredicates().size());
        definition.setId(routeDefinition.getId());
        List<GatewayPredicateDefinition> gatewayPredicateDefinitionList = routeDefinition.getPredicates();
        gatewayPredicateDefinitionList.forEach(gatewayPredicateDefinition -> {
            PredicateDefinition predicate = new PredicateDefinition();
            predicate.setArgs(gatewayPredicateDefinition.getArgs());
            predicate.setName(gatewayPredicateDefinition.getName());
            predicateDefinitionList.add(predicate);
        });
        definition.setPredicates(predicateDefinitionList);
        URI uri = UriComponentsBuilder.fromHttpUrl(routeDefinition.getUri()).build().toUri();
        definition.setUri(uri);
        return definition;
    }

}
