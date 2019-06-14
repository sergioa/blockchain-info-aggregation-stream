package info.blockchain.aggregation.infrastructure.repository

import java.time.Instant
import java.util.stream.Stream

interface Repository<T> {
    fun save(obj: T)
    fun find(from: Instant, to: Instant): Stream<T>
}
