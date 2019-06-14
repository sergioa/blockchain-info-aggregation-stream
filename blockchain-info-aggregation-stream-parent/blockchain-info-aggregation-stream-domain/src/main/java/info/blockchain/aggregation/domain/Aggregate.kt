package info.blockchain.aggregation.domain

class Aggregate(val count: Int?, val size: Int?, val amount: Long?) {

    data class Builder(
        var count: Int = 0,
        var size: Int = 0,
        var amount: Long = 0) {

        fun count(count: Int) = apply { this.count = count }
        fun size(size: Int) = apply { this.size = size }
        fun amount(amount: Long) = apply { this.amount = amount }
        fun build() = Aggregate(count, size, amount)
    }

}
