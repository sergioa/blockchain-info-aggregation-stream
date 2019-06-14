package info.blockchain.aggregation.adapter.websocket.server

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter

import java.util.Collections.singletonMap

@Configuration
class WebSocketServerConfiguration {

    @Autowired
    @Qualifier("aggregate-handler")
    private val webSocketHandler: WebSocketHandler? = null


    @Bean
    fun handlerMapping(): HandlerMapping {
        val simpleUrlHandlerMapping = SimpleUrlHandlerMapping()
        simpleUrlHandlerMapping.order = Ordered.HIGHEST_PRECEDENCE
        simpleUrlHandlerMapping.urlMap = singletonMap<String, WebSocketHandler>("/aggregate", webSocketHandler)
        return simpleUrlHandlerMapping
    }


    @Bean
    fun webSocketHandlerAdapter(): WebSocketHandlerAdapter {
        return WebSocketHandlerAdapter()
    }

}
