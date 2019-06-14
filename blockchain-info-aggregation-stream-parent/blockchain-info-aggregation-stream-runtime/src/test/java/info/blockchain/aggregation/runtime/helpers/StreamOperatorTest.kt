package info.blockchain.aggregation.runtime.helpers

import info.blockchain.aggregation.domain.Output
import info.blockchain.aggregation.domain.Transaction
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.stream.Stream

class StreamOperatorTest<T> {

    @Test
    fun should_return_zero_aggregate_when_empty_stream() {
        val aggregate = StreamOperator.aggregate(Stream.empty<Transaction>())

        assertEquals(0, aggregate.count)
        assertEquals(0, aggregate.size)
        assertEquals(0L, aggregate.amount)
    }

    @Test
    fun should_return_transaction_aggregation() {
        val aggregate = StreamOperator.aggregate(
                Stream.of<Transaction>(
                        Transaction(ONE_KILOBYTE, listOf(Output(3), Output(1))),
                        Transaction(ONE_KILOBYTE * 2, listOf(Output(4), Output(2)))
                )
        )

        assertEquals(2, aggregate.count)
        assertEquals(ONE_KILOBYTE * 3, aggregate.size)
        assertEquals(10L, aggregate.amount)
    }

    companion object {

        private const val ONE_KILOBYTE = 1024
    }
}
