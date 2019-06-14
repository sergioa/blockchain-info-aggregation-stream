package info.blockchain.aggregation.infrastructure.repository

import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import java.time.Instant
import java.util.concurrent.TimeUnit
import java.util.stream.Stream


class TimeEvictingRepository<T>(timeToLive: Long, timeUnit: TimeUnit) : Repository<T> {

    private val cache: Cache<Instant, T> = CacheBuilder.newBuilder().expireAfterWrite(timeToLive, timeUnit).build()

    override fun save(obj: T) {
        cache.put(Instant.now(), obj)
    }

    override fun find(from: Instant, to: Instant): Stream<T> {
        val map = cache.asMap()
        return map.keys.stream()
                .filter { instant -> instant == from || instant.isAfter(from) && instant.isBefore(to) }
                .map { map[it] }
    }
}
