package day09.a

import readInputLines
import util.IntVector
import shouldBe
import vec2
import kotlin.math.abs

fun main() {
    val moves = readInput()
    var h = vec2(0,0)
    var t = vec2(0,0)
    val tailVisited = HashSet<IntVector>()
    tailVisited.add(t)
    for (m in moves) {
        val (dir, n) = m
        for (step in 1..n) {
            h += dir
            var d = h - t
            if (abs(d[0]) == 2 || abs(d[1]) == 2)
                d = d.map { if (abs(it) == 2) it/2 else 0 }
            t = h - d
            tailVisited.add(t)
        }
    }
    shouldBe(6391, tailVisited.size)
}

fun readInput(): ArrayList<Pair<IntVector, Int>> {
    val r = ArrayList<Pair<IntVector, Int>>()
    readInputLines(9).forEach { line ->
        r.add(
            Pair(
                when (line[0]) {
                    'L' -> vec2(-1, 0)
                    'R' -> vec2(1, 0)
                    'U' -> vec2(0, 1)
                    'D' -> vec2(0, -1)
                    else -> throw RuntimeException()
                },
                line.substring(2).toInt()
            )
        )
    }
    return r
}
