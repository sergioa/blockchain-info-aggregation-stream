package info.blockchain.aggregation.infrastructure.stream;

public interface MessageStream<T> {

    MessagePublisher<T> getPublisher();

    MessageSubscriber<T> getSubscriber();

}
