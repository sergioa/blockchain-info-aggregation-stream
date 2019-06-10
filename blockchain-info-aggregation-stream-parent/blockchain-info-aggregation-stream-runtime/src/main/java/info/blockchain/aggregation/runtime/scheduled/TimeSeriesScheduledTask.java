package info.blockchain.aggregation.runtime.scheduled;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.blockchain.aggregation.domain.Aggregate;
import info.blockchain.aggregation.runtime.helpers.StreamOperator;
import info.blockchain.aggregation.domain.Transaction;
import info.blockchain.aggregation.infrastructure.repository.Repository;
import info.blockchain.aggregation.infrastructure.stream.MessageStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
public class TimeSeriesScheduledTask {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Repository<Transaction> repository;

    @Autowired
    private MessageStream<Aggregate> stream;


    @Scheduled(cron = "0 * * * * ?")
    public void timeSeriesWindow() throws Exception {

        Instant now = Instant.now();

        Aggregate aggregate = StreamOperator.aggregate(repository.find(now.minus(1, ChronoUnit.MINUTES), now));

        log.debug("{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(aggregate));

        stream.getPublisher().publish(aggregate);
    }

}
