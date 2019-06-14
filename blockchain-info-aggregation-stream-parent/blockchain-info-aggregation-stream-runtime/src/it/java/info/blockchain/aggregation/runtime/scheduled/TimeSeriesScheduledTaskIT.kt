package info.blockchain.aggregation.runtime.scheduled

import org.awaitility.Duration
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
import org.springframework.test.context.junit4.SpringRunner

import org.awaitility.Awaitility.await
import org.mockito.Mockito.atLeast
import org.mockito.Mockito.verify

@RunWith(SpringRunner::class)
@SpringBootTest
class TimeSeriesScheduledTaskIT {

    @SpyBean
    private val task: TimeSeriesScheduledTask? = null

    @Test
    fun should_be_called_once_in_one_minute_period() {
        await().atMost(Duration.TWO_MINUTES)
                .untilAsserted { verify(task!!, atLeast(1)).timeSeriesWindow() }
    }
}
