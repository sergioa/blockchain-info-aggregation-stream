package info.blockchain.aggregation.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties("lock_time", "ver", "vin_sz", "hash", "vout_sz", "relayed_by", "time", "tx_index", "inputs")
class Transaction(@JsonProperty("size") val size: Int = 0, @JsonProperty("out") val outputs: List<Output>? = null) {

}
