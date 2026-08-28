package xyz.hihasan.ledgerlite.core.model

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * A currency amount stored as an integer number of minor units (e.g. cents) to avoid
 * floating-point rounding errors in financial math.
 */
@JvmInline
value class Money(val minorUnits: Long) : Comparable<Money> {

    val majorUnits: BigDecimal
        get() = BigDecimal(minorUnits).movePointLeft(2)

    operator fun plus(other: Money) = Money(minorUnits + other.minorUnits)
    operator fun minus(other: Money) = Money(minorUnits - other.minorUnits)
    operator fun unaryMinus() = Money(-minorUnits)

    val isNegative: Boolean get() = minorUnits < 0
    val isZero: Boolean get() = minorUnits == 0L

    override fun compareTo(other: Money): Int = minorUnits.compareTo(other.minorUnits)

    companion object {
        val ZERO = Money(0)

        fun ofMajor(amount: BigDecimal): Money =
            Money(amount.movePointRight(2).setScale(0, RoundingMode.HALF_EVEN).toLong())

        fun ofMajor(amount: Double): Money = ofMajor(BigDecimal.valueOf(amount))

        fun sum(values: Iterable<Money>): Money = Money(values.sumOf { it.minorUnits })
    }
}
