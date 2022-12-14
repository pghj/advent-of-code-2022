package util

import java.lang.Integer.max
import java.lang.Integer.min

class IntRange(
    val from: Int,
    val to: Int,
) {

    val size: Int = to-from+1

    override fun toString(): String {
        return "[$from,$to]"
    }

    infix fun touches(other: IntRange): Boolean {
        return to + 1 == other.from || other.to + 1 == from
    }

    infix fun intersects(other: IntRange): Boolean {
        return from <= other.to && other.from <= to
    }

    infix fun union(other: IntRange): IntRange {
        check(this intersects other)
        val f = min(other.from, from)
        val t = max(other.to, to)
        return IntRange(f,t)
    }

    fun contains(v: Int): Boolean {
        return v in from..to
    }

    fun contains(other: IntRange): Boolean {
        return from <= other.from && other.to <= to
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as IntRange
        if (from != other.from) return false
        if (to != other.to) return false

        return true
    }

    override fun hashCode(): Int {
        var result = from
        result = 31 * result + to
        return result
    }

}