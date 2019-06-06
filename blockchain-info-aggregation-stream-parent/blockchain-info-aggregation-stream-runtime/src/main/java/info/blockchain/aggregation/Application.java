package info.blockchain.aggregation;

import info.blockchain.aggregation.infrastructure.stream.ReplaySubjectMessageStream;
import info.blockchain.aggregation.infrastructure.stream.MessageStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean("transaction-topic")
    public MessageStream transactionStream() {
        return new ReplaySubjectMessageStream();
    }

    @Bean("aggregate-topic")
    public MessageStream aggregateStream() {
        return new ReplaySubjectMessageStream();
    }
}
