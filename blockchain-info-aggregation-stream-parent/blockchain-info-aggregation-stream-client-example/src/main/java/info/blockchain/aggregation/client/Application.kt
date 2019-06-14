package info.blockchain.aggregation.client

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient
import reactor.core.scheduler.Schedulers
import java.net.URI

@SpringBootApplication
class Application : ApplicationRunner {

    @Value("#{new java.net.URI('\${websocket-server.uri}')}")
    private val websocketServerUri: URI? = null

    @Throws(Exception::class)
    override fun run(args: ApplicationArguments) {
        log.info("Connecting to {}", websocketServerUri)
        ReactorNettyWebSocketClient()
                .execute(websocketServerUri!!
                ) { session ->
                    session.receive()
                            .map<String> { it.payloadAsText }
                            .doOnNext { log.info(it) }
                            .doOnTerminate { log.debug("OnTerminate") }
                            .doOnError { throwable -> log.error("OnError", throwable) }
                            .then()
                }
                .subscribeOn(Schedulers.single())
                .block()

    }

    companion object {
        private val log = LoggerFactory.getLogger(Application::class.java.name)

        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }
}
