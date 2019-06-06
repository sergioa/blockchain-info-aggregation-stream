package info.blockchain.aggregation.infrastructure.stream;

@FunctionalInterface
public interface MessagePublisher<T> {
    T publish(T obj);
}
