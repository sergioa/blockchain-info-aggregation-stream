package info.blockchain.aggregation.controller.websocket.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Configuration
public class ClientConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public WebSocketClient webSocketClient() {
        return new StandardWebSocketClient();

    }

    @Bean
    public WebSocketHandler webSocketHandler() {
        return new ClientHandler();
    }

    @Bean
    public WebSocketConnectionManager webSocketConnectionManager(WebSocketClient webSocketClient,
                                                                 WebSocketHandler webSocketHandler,
                                                                 @Value("${blockchain-info.url}") String uriTemplate) {
        WebSocketConnectionManager manager = new WebSocketConnectionManager(
                webSocketClient,
                webSocketHandler,
                uriTemplate);
        manager.setAutoStartup(true);
        return manager;
    }

}
