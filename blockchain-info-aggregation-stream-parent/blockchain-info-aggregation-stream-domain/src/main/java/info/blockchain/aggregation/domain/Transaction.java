package info.blockchain.aggregation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@JsonIgnoreProperties({"lock_time", "ver", "vin_sz", "hash", "vout_sz", "relayed_by", "time", "tx_index", "inputs"})
@Value
public class Transaction {

    @JsonProperty("size")
    public final int size;

    @JsonProperty("out")
    public final List<Output> outputs;

}
