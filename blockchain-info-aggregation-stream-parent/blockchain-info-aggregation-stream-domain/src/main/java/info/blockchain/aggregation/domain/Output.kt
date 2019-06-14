package info.blockchain.aggregation.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties("spent", "tx_index", "type", "addr", "n", "script")
class Output(@JsonProperty("value") val value: Long = 0) {
}
