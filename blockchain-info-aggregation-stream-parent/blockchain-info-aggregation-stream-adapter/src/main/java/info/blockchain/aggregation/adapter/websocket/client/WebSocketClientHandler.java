package info.blockchain.aggregation.adapter.websocket.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.blockchain.aggregation.domain.Notification;
import info.blockchain.aggregation.domain.Transaction;
import info.blockchain.aggregation.infrastructure.repository.Repository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
public class WebSocketClientHandler extends TextWebSocketHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Repository<Transaction> repository;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.debug("afterConnectionEstablished");
        super.afterConnectionEstablished(session);
        session.sendMessage(new TextMessage("{\"op\":\"unconfirmed_sub\"}"));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.debug("handleTextMessage");
        super.handleTextMessage(session, message);

        Transaction transaction = objectMapper.readValue(message.getPayload(), Notification.class).transaction;

        log.info("{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(transaction));

        repository.save(transaction);

    }
}
