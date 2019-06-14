package info.blockchain.aggregation.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import lombok.Value

@JsonIgnoreProperties("op")
class Notification {

    @JsonProperty("x")
    val transaction: Transaction? = null

}

