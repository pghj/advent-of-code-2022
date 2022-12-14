package day03.a

import readInputLines
import shouldBe

fun main() {

    var acc = 0
    readInputLines(3).forEach {
        val a = it.substring( 0 until it.length/2)
        val b = it.substring( it.length/2 )
        val ca = a.toSet()
        val cb = b.toSet()
        val c = ca.intersect(cb).iterator().next()
        val p = itemPriority(c)
        acc += p
    }
    shouldBe(8123, acc)
}

fun itemPriority(c: Char): Int {
    if (c in 'a'..'z') return c - 'a' + 1
    if (c in 'A'..'Z') return c - 'A' + 27
    throw RuntimeException()
}
