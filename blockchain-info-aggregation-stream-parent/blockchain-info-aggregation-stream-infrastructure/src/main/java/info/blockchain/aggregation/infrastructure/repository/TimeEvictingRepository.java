package info.blockchain.aggregation.infrastructure.repository;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;


public class TimeEvictingRepository<T> implements Repository<T> {

    private Cache<Instant, T> cache;

    public TimeEvictingRepository(long timeToLive, TimeUnit timeUnit) {
        this.cache = CacheBuilder.newBuilder().expireAfterWrite(timeToLive, timeUnit).build();
    }

    @Override
    public void save(@Nonnull T obj) {
        cache.put(Instant.now(), obj);
    }

    @Override
    public Stream<T> find(@Nonnull Instant from, @Nonnull Instant to) {
        ConcurrentMap<Instant, T> map = cache.asMap();
        return map.keySet().stream()
                .filter(instant -> instant.equals(from) || (instant.isAfter(from) && instant.isBefore(to)))
                .map(map::get);
    }
}
