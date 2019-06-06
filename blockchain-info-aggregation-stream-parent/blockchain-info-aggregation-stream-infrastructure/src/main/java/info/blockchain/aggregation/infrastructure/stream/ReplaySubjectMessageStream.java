package info.blockchain.aggregation.infrastructure.stream;

import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.ReplayProcessor;

import java.util.function.Function;

public class ReplaySubjectMessageStream<T> implements MessageStream<T> {

    private final FluxProcessor<T, T> subject = ReplayProcessor.cacheLast();

    @Override
    public MessagePublisher<T> getPublisher() {
        return obj -> {
            subject.onNext(obj);
            return obj;
        };
    }

    @Override
    public MessageSubscriber<T> getSubscriber() {
        return () -> subject.map(Function.identity());
    }

}
