package info.blockchain.aggregation.controller.websocket.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.blockchain.aggregation.domain.Notification;
import info.blockchain.aggregation.domain.Transaction;
import info.blockchain.aggregation.infrastructure.stream.MessageStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
public class ClientHandler extends TextWebSocketHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier("transaction-topic")
    private MessageStream<Transaction> messageStream;

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

        messageStream.getPublisher().publish(transaction);

        log.info("{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(transaction));
    }
}
