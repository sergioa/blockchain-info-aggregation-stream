package info.blockchain.aggregation.adapter.websocket.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import static java.util.Collections.singletonMap;

@Configuration
public class WebSocketServerConfiguration {

    @Autowired
    @Qualifier("aggregate-handler")
    private WebSocketHandler webSocketHandler;


    @Bean
    public HandlerMapping handlerMapping() {
        SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
        simpleUrlHandlerMapping.setOrder(Ordered.HIGHEST_PRECEDENCE);
        simpleUrlHandlerMapping.setUrlMap(singletonMap("/aggregate", webSocketHandler));
        return simpleUrlHandlerMapping;
    }


    @Bean
    public WebSocketHandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

}
