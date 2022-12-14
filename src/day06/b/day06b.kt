package day06.b

import readInputLines
import shouldBe

fun main() {
    // Using a more efficient approach because of the increased marker size.
    val input = readInputLines(6)[0]
    val s = Scanner()
    val n = s.findMarker(input, 14)
    shouldBe(3588, n)
}

class Scanner {
    private val counters = IntArray(26)
    private var unique = 0
    private fun add(c: Char) {
        val n = ++counters[c - 'a']
        if (n == 1) unique++
    }
    private fun rm(c: Char) {
        val n = --counters[c - 'a']
        if (n == 0) unique--
    }
    fun findMarker(input: String, markerSize: Int): Int {
        for (i in input.indices) {
            add(input[i])
            if (i >= markerSize) rm(input[i-markerSize])
            if (unique == markerSize) return i+1
        }
        return -1
    }
}

