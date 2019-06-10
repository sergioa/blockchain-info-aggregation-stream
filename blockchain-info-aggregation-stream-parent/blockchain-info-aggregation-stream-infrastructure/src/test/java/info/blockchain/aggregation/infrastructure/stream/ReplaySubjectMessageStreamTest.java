package info.blockchain.aggregation.infrastructure.stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.awaitility.Awaitility.await;

public class ReplaySubjectMessageStreamTest {

    private ReplaySubjectMessageStream<String> stream;

    @Before
    public void setup() {
        this.stream = new ReplaySubjectMessageStream<>();
    }

    @Test
    public void should_replay_messages_to_observers() {

        final AtomicInteger signals = new AtomicInteger();
        Flux.from(stream.getSubscriber().publisher())
                .doOnNext(s -> signals.incrementAndGet())
                .subscribe();

        MessagePublisher<String> messagePublisher = stream.getPublisher();

        Stream.of("element1", "element2").forEach(messagePublisher::publish);

        await().timeout(500, TimeUnit.MILLISECONDS).until(() -> signals.get() == 2);

    }
}
