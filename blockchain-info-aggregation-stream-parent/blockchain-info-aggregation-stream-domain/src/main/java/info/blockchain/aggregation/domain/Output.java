package info.blockchain.aggregation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@JsonIgnoreProperties({"spent", "tx_index", "type", "addr", "n", "script"})
@Value
public class Output {

    @JsonProperty("value")
    public final long value;

}
