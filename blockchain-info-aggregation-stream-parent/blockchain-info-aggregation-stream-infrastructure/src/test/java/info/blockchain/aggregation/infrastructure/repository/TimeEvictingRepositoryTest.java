package info.blockchain.aggregation.infrastructure.repository;

import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;

public class TimeEvictingRepositoryTest {

    private static final long TIME_TO_LIVE = 5000;

    private TimeEvictingRepository<String> timeEvictingRepository;

    private Instant from;

    private Instant to;

    @Before
    public void setup() {
        timeEvictingRepository = new TimeEvictingRepository<>(TIME_TO_LIVE, TimeUnit.MILLISECONDS);
        from = Instant.now();
        to = Instant.now().plusSeconds(60);
    }

    @Test
    public void should_return_empty_set() {
        assertEquals("Invalid element count", 0, timeEvictingRepository.find(from, to).count());

    }

    @Test
    public void should_return_inserted_element() {
        timeEvictingRepository.save("hello");
        assertEquals("Invalid element count", 1, timeEvictingRepository.find(from, to).count());

    }

    @Test
    public void should_evict_element() {
        timeEvictingRepository.save("hello");

        assertEquals("Invalid element count", 1, timeEvictingRepository.find(from, to).count());

        await().atMost(2 * TIME_TO_LIVE, TimeUnit.SECONDS).until(() -> timeEvictingRepository.find(from, to).count() == 0);
    }
}