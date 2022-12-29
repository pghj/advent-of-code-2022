package day15.b

import day15.a.distance
import day15.a.rangeAtRow
import day15.a.read
import shouldBe
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
        for (r in e) {
            if ((r.from-1) in 0..4000000) return (r.from-1) * 4000000L + row
            else if ((r.to+1) in 0 .. 4000000) return (r.to+1) * 4000000L + row
        }
    }
    throw RuntimeException()
}
