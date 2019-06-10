package info.blockchain.aggregation.adapter.websocket.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.blockchain.aggregation.domain.Aggregate;
import info.blockchain.aggregation.infrastructure.stream.MessageStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component("aggregate-handler")
public class WebSocketServerHandler implements WebSocketHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageStream<Aggregate> stream;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(
                Flux.from(stream.getSubscriber().publisher())
                        .map(obj -> {
                            try {
                                return session.textMessage(objectMapper.writeValueAsString(obj));
                            } catch (JsonProcessingException e) {
                                log.error("String serialization exception", e);
                                return null;
                            }
                        }).doOnError(throwable -> log.error("OnError", throwable))
        );
    }
}
