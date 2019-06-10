package info.blockchain.aggregation.infrastructure.stream;

import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.FluxProcessor;

import java.util.function.Function;

public class ReplaySubjectMessageStream<T> implements MessageStream<T> {

    private final FluxProcessor<T, T> subject = DirectProcessor.create();

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
