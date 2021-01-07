package org.starrier.imperator.route;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.web.util.UriComponentsBuilder;
import org.starrier.imperator.model.GatewayPredicateDefinition;
import org.starrier.imperator.model.GatewayRouteDefinition;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author starrier
 * @date 2021/1/7
 */
public class RouteRepresentApplicationTest {

    @Test
    public void testAdd() {
    }

    @Test
    public void testUpdate() {
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
     */
    @Test
    public void testDelete() {
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


}