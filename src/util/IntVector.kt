package util

class IntVector {

    val coefficient: IntArray

    constructor(coefficient: IntArray) {
        this.coefficient = coefficient.copyOf()
    }

    constructor(dim: Int, init: (index: Int) -> Int) {
        this.coefficient = IntArray(dim, init)
    }

    companion object {
        fun of(vararg coefficient: Int) : IntVector {
            return IntVector(coefficient.size) { i -> coefficient[i] }
        }

        fun parse(s: String): IntVector {
            val t = s.split(",").map { it.toInt() }.toIntArray()
            check(t.size == 2)
            return IntVector(t)
        }
    }

    fun rotate(fromAxis: Int, toAxis: Int): IntVector {
        val c = coefficient.copyOf()
        c[toAxis] = coefficient[fromAxis]
        c[fromAxis] = - coefficient[toAxis]
        return IntVector(c)
    }

    fun copy(): IntVector {
        return IntVector(coefficient.copyOf())
    }

    fun toIntArray(): IntArray {
        return coefficient.copyOf()
    }

    operator fun times(scalar: Int): IntVector {
        return IntVector(IntArray(coefficient.size) { i -> coefficient[i] * scalar })
    }

    operator fun plus(that: IntVector): IntVector {
        ensureSameDimension(that)
        return IntVector(IntArray(coefficient.size) { i -> coefficient[i] + that.coefficient[i] })
    }

    operator fun minus(that: IntVector): IntVector {
        ensureSameDimension(that)
        return IntVector(IntArray(coefficient.size) { i -> coefficient[i] - that.coefficient[i] })
    }

    fun map(f: (Int) -> Int ) : IntVector {
        return IntVector(IntArray(coefficient.size) { i -> f(coefficient[i]) })
    }
    fun mapIndexed(f: (index: Int, value: Int) -> Int ) : IntVector {
        return IntVector(IntArray(coefficient.size) { i -> f(i, coefficient[i]) })
    }

    private fun ensureSameDimension(that: IntVector) {
        if (coefficient.size != that.coefficient.size) throw IllegalArgumentException()
    }

    operator fun get(axis: Int): Int {
        return coefficient[axis]
    }

    override fun toString(): String {
        return coefficient.contentToString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as IntVector
        return coefficient.contentEquals(other.coefficient)
    }

    override fun hashCode(): Int {
        return coefficient.contentHashCode()
    }

    operator fun unaryMinus(): IntVector {
        val a = coefficient.copyOf()
        for (i in a.indices) a[i] = -a[i]
        return IntVector(a)
    }
}