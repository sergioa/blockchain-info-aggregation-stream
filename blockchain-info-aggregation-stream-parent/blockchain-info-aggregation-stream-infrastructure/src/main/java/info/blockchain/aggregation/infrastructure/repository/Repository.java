package info.blockchain.aggregation.infrastructure.repository;

import java.time.Instant;
import java.util.stream.Stream;

public interface Repository<T> {
    void save(T obj);
    Stream<T> find(Instant from, Instant to);
}
