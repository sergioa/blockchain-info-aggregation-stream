package info.blockchain.aggregation.runtime;

import info.blockchain.aggregation.domain.Transaction;
import info.blockchain.aggregation.infrastructure.repository.Repository;
import info.blockchain.aggregation.infrastructure.repository.TimeEvictingRepository;
import info.blockchain.aggregation.infrastructure.stream.MessageStream;
import info.blockchain.aggregation.infrastructure.stream.ReplaySubjectMessageStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.TimeUnit;

@EnableScheduling
@SpringBootApplication
@ComponentScan("info.blockchain.aggregation")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public MessageStream stream() {
        return new ReplaySubjectMessageStream();
    }

    @Bean
    public Repository<Transaction> repository(@Value("${repository.time-to-live:120}") long timeToLive) {
        return new TimeEvictingRepository<>(timeToLive, TimeUnit.SECONDS);
    }

}
