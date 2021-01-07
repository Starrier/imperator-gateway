package org.starrier.imperator.route;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author Starrier
 * @date 2019/4/14
 */
@Service
public class RouteRepresentApplication implements ApplicationEventPublisherAware {

    private final Logger LOGGER = LoggerFactory.getLogger(RouteRepresentApplication.class);

    private final RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationEventPublisher publisher;

    @Autowired
    public RouteRepresentApplication(RouteDefinitionWriter routeDefinitionWriter) {
        this.routeDefinitionWriter = routeDefinitionWriter;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
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
            LOGGER.error("update fail,not find route  routeId: [{}]", e.toString(), e);
            return "update fail,not find route  routeId: " + definition.getId();
        }
        try {
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "success";
        } catch (Exception e) {
            LOGGER.error("update route  fail:[{}]", e.toString(), e);
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

}
