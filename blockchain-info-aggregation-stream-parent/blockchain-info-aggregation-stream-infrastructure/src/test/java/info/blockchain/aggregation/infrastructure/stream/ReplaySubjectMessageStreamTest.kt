package info.blockchain.aggregation.infrastructure.stream

import org.awaitility.Awaitility.await
import org.junit.Before
import org.junit.Test
import reactor.core.publisher.Flux
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import java.util.stream.Stream

class ReplaySubjectMessageStreamTest {

    private var stream: ReplaySubjectMessageStream<String>? = null

    @Before
    fun setup() {
        this.stream = ReplaySubjectMessageStream()
    }

    @Test
    fun should_replay_messages_to_observers() {

        val signals = AtomicInteger()
        Flux.from(stream!!.subscriber.publisher())
                .doOnNext { signals.incrementAndGet() }
                .subscribe()

        val messagePublisher = stream!!.publisher

        Stream.of("element1", "element2").forEach { messagePublisher.publish(it) }

        await().timeout(500, TimeUnit.MILLISECONDS).until { signals.get() == 2 }

    }
}
