package info.blockchain.aggregation.runtime.scheduled

import com.fasterxml.jackson.databind.ObjectMapper
import info.blockchain.aggregation.domain.Aggregate
import info.blockchain.aggregation.runtime.helpers.StreamOperator
import info.blockchain.aggregation.domain.Transaction
import info.blockchain.aggregation.infrastructure.repository.Repository
import info.blockchain.aggregation.infrastructure.stream.MessageStream
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

import java.time.Instant
import java.time.temporal.ChronoUnit


@Component
open class TimeSeriesScheduledTask {

    @Autowired
    private val objectMapper: ObjectMapper? = null

    @Autowired
    private val repository: Repository<Transaction>? = null

    @Autowired
    private val stream: MessageStream<Aggregate>? = null


    @Scheduled(cron = "0 * * * * ?")
    @Throws(Exception::class)
    fun timeSeriesWindow() {

        val now = Instant.now()

        val aggregate = StreamOperator.aggregate(repository!!.find(now.minus(1, ChronoUnit.MINUTES), now))

        log.debug("{}", objectMapper!!.writerWithDefaultPrettyPrinter().writeValueAsString(aggregate))

        stream!!.publisher.publish(aggregate)
    }

    companion object {
        private val log = LoggerFactory.getLogger(TimeSeriesScheduledTask::class.java.name)
    }

}
