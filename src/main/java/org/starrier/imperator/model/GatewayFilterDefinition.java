package org.starrier.imperator.model;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 过滤器定义模型
 *
 * @author Starrier
 * @date 2019/4/14
 */
@Getter
@Setter
public class GatewayFilterDefinition {

    /**
     * Filter Name
     */
    private String name;

    /**
     * Corresponding routing rule.
     */
    private Map<String, String> args = new LinkedHashMap<>();

}
