package info.blockchain.aggregation.runtime.helpers;

import info.blockchain.aggregation.domain.Aggregate;
import info.blockchain.aggregation.domain.Transaction;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;


public class StreamOperator {

    public static Aggregate aggregate(Stream<Transaction> stream) {

        Function<Stream<Transaction>, Aggregate> function = s -> {
            final AtomicInteger count = new AtomicInteger(), size = new AtomicInteger();
            final AtomicLong amount = new AtomicLong();

            s.forEach(transaction -> {
                count.incrementAndGet();
                size.addAndGet(transaction.size);
                ofNullable(transaction.outputs).ifPresent(
                        array -> array.forEach(output -> amount.addAndGet(output.value))
                );
            });
            return Aggregate.builder()
                    .count(count.get())
                    .size(size.get())
                    .amount(amount.get())
                    .build();
        };

        return function.apply(stream);
    }
}
