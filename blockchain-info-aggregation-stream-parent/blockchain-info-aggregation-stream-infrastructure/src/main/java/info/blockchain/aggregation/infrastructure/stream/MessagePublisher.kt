package info.blockchain.aggregation.infrastructure.stream

@FunctionalInterface
interface MessagePublisher<T> {
    fun publish(obj: T): T
}
