package info.blockchain.aggregation.runtime.helpers;

import info.blockchain.aggregation.domain.Aggregate;
import info.blockchain.aggregation.domain.Output;
import info.blockchain.aggregation.domain.Transaction;
import org.junit.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class StreamOperatorTest {

    private static final int ONE_KILOBYTE = 1024;

    @Test
    public void should_return_zero_aggregate_when_empty_stream() {
        Aggregate aggregate = StreamOperator.aggregate(Stream.empty());

        assertEquals(0, aggregate.count);
        assertEquals(0, aggregate.size);
        assertEquals(0, aggregate.amount);
    }

    @Test
    public void should_return_transaction_aggregation() {
        Aggregate aggregate = StreamOperator.aggregate(
                Stream.of(
                        new Transaction(ONE_KILOBYTE, List.of(new Output(3), new Output(1))),
                        new Transaction(ONE_KILOBYTE * 2, List.of(new Output(4), new Output(2)))
                )
        );

        assertEquals(2, aggregate.count);
        assertEquals(ONE_KILOBYTE * 3, aggregate.size);
        assertEquals(10, aggregate.amount);
    }
}
