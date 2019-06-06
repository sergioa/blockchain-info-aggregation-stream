package info.blockchain.aggregation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@JsonIgnoreProperties({"op"})
@Value
public class Notification {

    @JsonProperty("x")
    public final Transaction transaction;

}

