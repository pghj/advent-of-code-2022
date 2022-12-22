package util

class IntVector {

    val coefficient: IntArray

    val dim: Int
        get() { return coefficient.size }

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

        fun unit(dim: Int, axis: Int): IntVector {
            return IntVector(dim) { if (it == axis) 1 else 0 }
        }
    }

    fun rotate(fromAxis: Int, toAxis: Int): IntVector {
        val c = coefficient.copyOf()
        c[toAxis] = coefficient[fromAxis]
        c[fromAxis] = - coefficient[toAxis]
        return IntVector(c)
    }

    fun rotate(fromAxis: Int, toAxis: Int, pivot: IntVector): IntVector {
        val c = coefficient.copyOf()
        c[toAxis] = coefficient[fromAxis] - pivot[fromAxis] + pivot[toAxis]
        c[fromAxis] = - coefficient[toAxis] + pivot[fromAxis] + pivot[toAxis]
        return IntVector(c)
    }

    /**
     * Rotate 180 degrees.
     */
    fun rotate2(fromAxis: Int, toAxis: Int): IntVector {
        val c = coefficient.copyOf()
        c[fromAxis] = - coefficient[fromAxis]
        c[toAxis] = - coefficient[toAxis]
        return IntVector(c)
    }

    /**
     * Rotate 180 degrees around pivot.
     */
    fun rotate2(fromAxis: Int, toAxis: Int, pivot: IntVector): IntVector {
        val c = coefficient.copyOf()
        c[fromAxis] = - coefficient[fromAxis] + 2*pivot[fromAxis]
        c[toAxis] = - coefficient[toAxis] + 2*pivot[toAxis]
        return IntVector(c)
    }

    /**
     * Rotate 90 degrees. Arguments must be perpendicular unit vectors.
     */
    fun rotate(from: IntVector, to: IntVector) : IntVector {
        check(from dot from == 1) // unit length
        check(to dot to == 1) // unit length
        check(from dot to == 0) // perpendicular
        val p = of(from dot this, to dot this)
        val q = this - from*p[0] - to*p[1]
        val r = p.rotate(0,1)
        return from * r[0] + to * r[1] + q
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

    fun all(function: (v: Int) -> Boolean): Boolean {
        return coefficient.all(function)
    }

    fun any(function: (v: Int) -> Boolean): Boolean {
        return coefficient.any(function)
    }

    infix fun dot(other: IntVector): Int {
        ensureSameDimension(other)
        var r = 0
        for (i in 0 until dim) r += this[i] * other[i]
        return r
    }

    infix fun cross(b: IntVector): IntVector {
        check(dim == 3 && b.dim == 3)
        val a = this
        return of(
            a[1]*b[2]-a[2]*b[1],
            a[2]*b[0]-a[0]*b[2],
            a[0]*b[1]-a[1]*b[0]
        )
    }


}