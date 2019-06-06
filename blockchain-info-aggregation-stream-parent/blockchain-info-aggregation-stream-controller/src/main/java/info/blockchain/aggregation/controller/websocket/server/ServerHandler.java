package info.blockchain.aggregation.controller.websocket.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.blockchain.aggregation.domain.Transaction;
import info.blockchain.aggregation.infrastructure.stream.MessageStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component("aggregate-handler")
public class ServerHandler implements WebSocketHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier("transaction-topic")
    private MessageStream<Transaction> messageStream;

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        return webSocketSession.send(
                Flux.from(messageStream.getSubscriber().subscribe()).map(obj -> {
                    try {
                        return webSocketSession.textMessage(objectMapper.writeValueAsString(obj));
                    } catch (JsonProcessingException e) {
                        log.error("serialization error", e);
                        return null;
                    }
                })
        );
    }
}
