package info.blockchain.aggregation.scheduled;

import info.blockchain.aggregation.domain.Aggregate;
import info.blockchain.aggregation.infrastructure.stream.MessageStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Slf4j
@Component
public class TimeSeriesScheduledTask {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    @Autowired
    MessageStream<Aggregate> messageStream;

    @Scheduled(fixedRateString = "${time-series.rate:6000}")
    public void reportCurrentTime() {
        messageStream.getPublisher().publish(new Aggregate(0, 0, 0));
    }

}
