package info.blockchain.aggregation.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import reactor.core.scheduler.Schedulers;

import java.net.URI;

@Slf4j
@SpringBootApplication
public class Application implements ApplicationRunner {

    @Value("#{new java.net.URI('${websocket-server.uri}')}")
    private URI websocketServerUri;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Connecting to {}", websocketServerUri);
        new ReactorNettyWebSocketClient()
                .execute(websocketServerUri,
                        session ->
                                session.receive()
                                        .map(WebSocketMessage::getPayloadAsText)
                                        .doOnNext(log::info)
                                        .doOnTerminate(() -> log.debug("OnTerminate"))
                                        .doOnError(throwable -> log.error("OnError", throwable))
                                        .then()
                )
                .subscribeOn(Schedulers.single())
                .block();

    }
}
