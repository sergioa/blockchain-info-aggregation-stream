package info.blockchain.aggregation.infrastructure.stream;

import org.reactivestreams.Publisher;

@FunctionalInterface
public interface MessageSubscriber<T> {
    Publisher<T> publisher();
}
