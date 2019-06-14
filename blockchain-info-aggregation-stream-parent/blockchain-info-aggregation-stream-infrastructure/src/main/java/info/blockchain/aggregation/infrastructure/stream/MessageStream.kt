package info.blockchain.aggregation.infrastructure.stream

interface MessageStream<T> {

    val publisher: MessagePublisher<T>

    val subscriber: MessageSubscriber<T>

}
