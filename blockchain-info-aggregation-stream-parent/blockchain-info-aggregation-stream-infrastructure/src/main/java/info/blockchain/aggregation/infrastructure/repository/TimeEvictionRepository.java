package info.blockchain.aggregation.infrastructure.repository;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.time.Instant;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class TimeEvictionRepository<T> implements Repository<T> {

    private Cache<Instant, T> cache;

    public TimeEvictionRepository(long timeToLive) {
        this.cache = CacheBuilder.newBuilder().expireAfterWrite(timeToLive, TimeUnit.MILLISECONDS).build();
    }

    @Override
    public void save(T obj) {
        cache.put(Instant.now(), obj);
    }

    @Override
    public Stream<T> find(Instant from, Instant to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException();
        }
        ConcurrentMap<Instant, T> map = cache.asMap();
        return map.keySet().stream()
                .filter(instant -> instant.equals(from) || (instant.isAfter(from)) && instant.isBefore(to))
                .map(map::get);
    }
}
