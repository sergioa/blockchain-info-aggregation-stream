package info.blockchain.aggregation.runtime.helpers

import info.blockchain.aggregation.domain.Aggregate
import info.blockchain.aggregation.domain.Transaction
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong
import java.util.stream.Stream


object StreamOperator {

    fun aggregate(stream: Stream<Transaction>): Aggregate {

        return { s: Stream<Transaction> ->
            val count = AtomicInteger()
            val size = AtomicInteger()
            val amount = AtomicLong()

            s.forEach {
                count.incrementAndGet()
                size.addAndGet(it.size)
                it.outputs?.let { it.forEach { amount.addAndGet(it.value) } }
            }
            Aggregate.Builder()
                    .count(count.get())
                    .size(size.get())
                    .amount(amount.get())
                    .build()
        }.invoke(stream)
    }
}
