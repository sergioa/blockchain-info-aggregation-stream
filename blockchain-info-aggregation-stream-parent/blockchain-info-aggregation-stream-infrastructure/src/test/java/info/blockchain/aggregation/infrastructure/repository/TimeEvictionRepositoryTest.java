package info.blockchain.aggregation.infrastructure.repository;

import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;

public class TimeEvictionRepositoryTest {

    private static final long TIME_TO_LIVE = 5000;

    private TimeEvictionRepository<String> timeEvictionRepository;

    private Instant from;

    private Instant to;

    @Before
    public void setup() {
        timeEvictionRepository = new TimeEvictionRepository<>(TIME_TO_LIVE);
        from = Instant.now();
        to = Instant.now().plusSeconds(60);
    }

    @Test
    public void should_return_empty_set() {
        assertEquals("Invalid element count", 0, timeEvictionRepository.find(from, to).count());

    }

    @Test
    public void should_return_inserted_element() {
        timeEvictionRepository.save("hello");
        assertEquals("Invalid element count", 1, timeEvictionRepository.find(from, to).count());

    }

    @Test
    public void should_evict_element() {
        timeEvictionRepository.save("hello");

        assertEquals("Invalid element count", 1, timeEvictionRepository.find(from, to).count());

        await().atMost(2 * TIME_TO_LIVE, TimeUnit.SECONDS).until(() -> timeEvictionRepository.find(from, to).count() == 0);
    }
}