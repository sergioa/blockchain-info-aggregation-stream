package info.blockchain.aggregation.adapter.websocket.client

import com.fasterxml.jackson.databind.ObjectMapper
import info.blockchain.aggregation.domain.Notification
import info.blockchain.aggregation.domain.Transaction
import info.blockchain.aggregation.infrastructure.repository.Repository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class WebSocketClientHandler : TextWebSocketHandler() {

    @Autowired
    private val objectMapper: ObjectMapper? = null

    @Autowired
    private val repository: Repository<Transaction>? = null

    @Throws(Exception::class)
    override fun afterConnectionEstablished(session: WebSocketSession?) {
        log.debug("afterConnectionEstablished")
        super.afterConnectionEstablished(session!!)
        session.sendMessage(TextMessage("{\"op\":\"unconfirmed_sub\"}"))
    }

    @Throws(Exception::class)
    override fun handleTextMessage(session: WebSocketSession?, message: TextMessage?) {
        log.debug("handleTextMessage")
        super.handleTextMessage(session!!, message!!)

        val transaction = objectMapper!!.readValue(message.payload, Notification::class.java).transaction

        log.info("{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(transaction))

        transaction?.let { repository!!.save(it) }

    }

    companion object {
        private val log = LoggerFactory.getLogger(WebSocketClientHandler::class.java.name)
    }
}
