package org.starrier.imperator.route;


import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.starrier.imperator.model.GatewayPredicateDefinition;
import org.starrier.imperator.model.GatewayRouteDefinition;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Starrier
 * @date 2019/4/14
 */
@Service
public class DynamicRouteServiceImpl implements ApplicationEventPublisherAware {

    private final RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationEventPublisher publisher;

    @Autowired
    public DynamicRouteServiceImpl(RouteDefinitionWriter routeDefinitionWriter) {
        this.routeDefinitionWriter = routeDefinitionWriter;
    }

    /**
     * spring:
     * cloud:
     * gateway:
     * routes: #当访问http://localhost:8080/baidu,直接转发到https://www.baidu.com/
     * - id: baidu_route
     * uri: http://baidu.com:80/
     * predicates:
     * - Path=/baidu
     *
     * @param args
     */

    public static void main(String[] args) {
        GatewayRouteDefinition definition = new GatewayRouteDefinition();
        GatewayPredicateDefinition predicate = new GatewayPredicateDefinition();
        Map<String, String> predicateParams = new HashMap<>(8);
        definition.setId("jd_route");
        predicate.setName("Path");
        predicateParams.put("pattern", "/jd");
        predicate.setArgs(predicateParams);
        definition.setPredicates(Arrays.asList(predicate));
        String uri = "http://www.jd.com";
        //URI uri = UriComponentsBuilder.fromHttpUrl("http://www.baidu.com").build().toUri();
        definition.setUri(uri);
        System.out.println("definition:" + JSON.toJSONString(definition));


        RouteDefinition definition1 = new RouteDefinition();
        PredicateDefinition predicate1 = new PredicateDefinition();
        Map<String, String> predicateParams1 = new HashMap<>(8);
        definition1.setId("baidu_route");
        predicate1.setName("Path");
        predicateParams1.put("pattern", "/baidu");
        predicate1.setArgs(predicateParams1);
        definition1.setPredicates(Arrays.asList(predicate1));
        URI uri1 = UriComponentsBuilder.fromHttpUrl("http://www.baidu.com").build().toUri();
        definition1.setUri(uri1);
        System.out.println("definition1：" + JSON.toJSONString(definition1));

    }

    /**
     * Add routes.
     *
     * @param definition {@link RouteDefinition}
     * @return Result message
     */
    public String add(RouteDefinition definition) {
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return "success";
    }

    /**
     * Update routes.
     *
     * @param definition {@link RouteDefinition}
     * @return
     */
    public String update(RouteDefinition definition) {
        try {
            this.routeDefinitionWriter.delete(Mono.just(definition.getId()));
        } catch (Exception e) {
            return "update fail,not find route  routeId: " + definition.getId();
        }
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "success";
        } catch (Exception e) {
            return "update route  fail";
        }


    }

    /**
     * Delete rotues.
     *
     * @param id
     * @return
     */
    @SneakyThrows(Exception.class)
    public String delete(String id) {
        this.routeDefinitionWriter.delete(Mono.just(id));
        return "delete success";
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }


}
