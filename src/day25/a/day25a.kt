package day25.a

import readInputLines
import shouldBe

fun main() {
    val l = read()
    val v = printSnafu(l.sumOf { parseSnafu(it) })
    shouldBe("2=10---0===-1--01-20", v)
}

fun valueOfSnafuChar(c: Char): Long = "=-012".indexOf(c) - 2L

fun snafuDigitToChar(d: Int): Char = "=-012"[d+2]

fun parseSnafu(s: String): Long {
    var acc = 0L
    var p = 1L
    for (i in s.indices.reversed()) {
        acc += p * valueOfSnafuChar(s[i])
        p *= 5
    }
    return acc
}

fun printSnafu(v: Long): String {
    if (v == 0L) return "0"
    val sb = StringBuilder()
    var x = v
    while (x != 0L) {
        var r = x % 5L
        x /= 5L
        if (r > 2) {
            r -= 5L
            x++
        }
        sb.append(snafuDigitToChar(r.toInt()))
    }
    return sb.reversed().toString()
}

fun read(): List<String> {
    return readInputLines(25).filter { it.isNotBlank() }
}
