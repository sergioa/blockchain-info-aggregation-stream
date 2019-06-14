package info.blockchain.aggregation.adapter.websocket.server

import com.fasterxml.jackson.databind.ObjectMapper
import info.blockchain.aggregation.domain.Aggregate
import info.blockchain.aggregation.infrastructure.stream.MessageStream
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component("aggregate-handler")
class WebSocketServerHandler : WebSocketHandler {

    @Autowired
    private val objectMapper: ObjectMapper? = null

    @Autowired
    private val stream: MessageStream<Aggregate>? = null

    override fun handle(session: WebSocketSession): Mono<Void> {
        return session.send(
                Flux.from(stream!!.subscriber.publisher())
                        .map { session.textMessage(objectMapper!!.writeValueAsString(it)) }
                        .doOnError { log.error("onerror $it") }
        )
    }

    companion object {
        private val log = LoggerFactory.getLogger(WebSocketServerHandler::class.java.name)
    }
}
