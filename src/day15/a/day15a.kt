package day15.a

import readInputLines
import shouldBe
import util.IntRange
import util.IntRangeSet
import util.IntVector
import vec2
import java.util.regex.Pattern
import kotlin.math.abs

typealias Input = ArrayList<Pair<IntVector, IntVector>>

fun main() {
    val input = read()
    val row = 2000000
    val e = IntRangeSet()
    for (p in input) {
        val l = distance(p.first, p.second)
        rangeAtRow(p.first, l, row)?.let { e.add(it) }
    }
    // beacons in that row and detection range are not to be counted
    val b = input.map { it.second }.filter { it[1] == row }.toSet().filter { e.contains(it[0]) }.size
    // size of detected range
    val n = e.sumOf { it.size }
    shouldBe(4907780, n-b)
}

fun distance(a: IntVector, b: IntVector): Int {
    return abs(a[0] - b[0]) + abs(a[1]-b[1])
}

fun rangeAtRow(sensor: IntVector, dist: Int, y: Int): IntRange? {
    val w = 1 + 2*(dist - abs(sensor[1] - y))
    if (w <= 0) return null
    return IntRange(sensor[0]-w/2, sensor[0]+w/2)
}

fun read(): Input {
    val g = Input()
    readInputLines(15).forEach { line ->
        val i = line.split(Pattern.compile("[^0-9-]+")).filter { it.isNotBlank() }.map { it.toInt() }
        val s = vec2(i[0], i[1])
        val b = vec2(i[2], i[3])
        g.add(Pair(s,b))
    }
    return g
}
