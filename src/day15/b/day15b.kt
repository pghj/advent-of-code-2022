package day15.b

import day15.a.distance
import day15.a.rangeAtRow
import day15.a.read
import shouldBe
import util.IntRange
import util.IntRangeSet
import util.IntVector

typealias Input = ArrayList<Pair<IntVector, IntVector>>

fun main() {
    val input = read()
    val tf = findTuningFreq(input)
    shouldBe(13639962836448, tf)
}

fun findTuningFreq(input: Input): Long {
    for (row in 0..4000000) {
        val e = IntRangeSet()
        for (p in input) {
            val l = distance(p.first, p.second)
            rangeAtRow(p.first, l, row)?.let { e.add(it)}
        }
        // might have been first or last coordinate, but it happens to be between two ranges
        if (e.size == 2) {
            val x = e.get(0).to + 1
            return x * 4000000L + row
        }
    }
    throw RuntimeException()
}
