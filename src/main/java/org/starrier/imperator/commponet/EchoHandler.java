package org.starrier.imperator.commponet;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

/**
 * @author Starrier
 * @date 2019/4/18.
 * <p>
 * Description :
 */
@Component
public class EchoHandler implements WebSocketHandler {
    @Override
    public Mono<Void> handle(final WebSocketSession session) {
        return session.send(session.receive().map(msg -> session.textMessage("echo -> " + msg.getPayloadAsText())));
    }
}
