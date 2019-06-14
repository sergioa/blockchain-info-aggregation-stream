package info.blockchain.aggregation.adapter.websocket.client

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.client.WebSocketClient
import org.springframework.web.socket.client.WebSocketConnectionManager
import org.springframework.web.socket.client.standard.StandardWebSocketClient

@Configuration
class WebSocketClientConfiguration {

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper()
    }

    @Bean
    fun webSocketClient(): WebSocketClient {
        return StandardWebSocketClient()

    }

    @Bean
    fun webSocketHandler(): WebSocketHandler {
        return WebSocketClientHandler()
    }

    @Bean
    fun webSocketConnectionManager(webSocketClient: WebSocketClient,
                                   webSocketHandler: WebSocketHandler,
                                   @Value("\${blockchain-info.url}") uriTemplate: String): WebSocketConnectionManager {
        val manager = WebSocketConnectionManager(
                webSocketClient,
                webSocketHandler,
                uriTemplate)
        manager.isAutoStartup = true
        return manager
    }

}
