package info.blockchain.aggregation.infrastructure.stream

import org.reactivestreams.Publisher
import reactor.core.publisher.DirectProcessor

class ReplaySubjectMessageStream<T> : MessageStream<T> {

    private val subject = DirectProcessor.create<T>()

    override val publisher: MessagePublisher<T>
        get() = object : MessagePublisher<T> {
            override fun publish(obj: T): T {
                subject.onNext(obj)
                return obj
            }
        }

    override val subscriber: MessageSubscriber<T>
        get() = object : MessageSubscriber<T> {
            override fun publisher(): Publisher<T> {
                return subject.map { x -> x }
            }
        }


}
