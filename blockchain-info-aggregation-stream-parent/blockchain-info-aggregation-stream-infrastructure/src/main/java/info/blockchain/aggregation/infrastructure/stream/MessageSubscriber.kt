package info.blockchain.aggregation.infrastructure.stream

import org.reactivestreams.Publisher

@FunctionalInterface
interface MessageSubscriber<T> {
    fun publisher(): Publisher<T>
}
