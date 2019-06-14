package info.blockchain.aggregation.infrastructure.repository

import org.awaitility.Awaitility.await
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.Instant
import java.util.concurrent.TimeUnit

class TimeEvictingRepositoryTest {

    private var timeEvictingRepository: TimeEvictingRepository<String>? = null

    private var from: Instant? = null

    private var to: Instant? = null

    @Before
    fun setup() {
        timeEvictingRepository = TimeEvictingRepository(TIME_TO_LIVE, TimeUnit.MILLISECONDS)
        from = Instant.now()
        to = Instant.now().plusSeconds(60)
    }

    @Test
    fun should_return_empty_set() {
        assertEquals("Invalid element count", 0, timeEvictingRepository!!.find(from!!, to!!).count())

    }

    @Test
    fun should_return_inserted_element() {
        timeEvictingRepository!!.save("hello")
        assertEquals("Invalid element count", 1, timeEvictingRepository!!.find(from!!, to!!).count())

    }

    @Test
    fun should_evict_element() {
        timeEvictingRepository!!.save("hello")

        assertEquals("Invalid element count", 1, timeEvictingRepository!!.find(from!!, to!!).count())

        await().atMost(2 * TIME_TO_LIVE, TimeUnit.SECONDS).until { 0L == timeEvictingRepository!!.find(from!!, to!!).count() }
    }

    companion object {

        private const val TIME_TO_LIVE: Long = 5000
    }
}