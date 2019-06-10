package info.blockchain.aggregation.domain;

import lombok.Builder;

@Builder
public class Aggregate {

    public final int count;
    public final int size;
    public final long amount;

}
