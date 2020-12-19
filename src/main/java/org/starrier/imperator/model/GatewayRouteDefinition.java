package org.starrier.imperator.model;


import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Gateway的路由定义模型
 *
 * @author Starrier
 * @date 2019/4/14
 */
@Getter
@Setter
public class GatewayRouteDefinition {

    /**
     * 路由的Id
     */
    private String id;

    /**
     * 路由断言集合配置
     */
    private List<GatewayPredicateDefinition> predicates = Lists.newArrayList();

    /**
     * 路由过滤器集合配置
     */
    private List<GatewayFilterDefinition> filters = Lists.newArrayList();

    /**
     * 路由规则转发的目标uri
     */
    private String uri;

    /**
     * 路由执行的顺序
     */
    private int order = 0;
}
