package day12.b

import readInputLines
import shouldBe
import util.IntGrid
import util.IntVector
import vec2
import java.lang.Integer.min

fun main() {
    val map = day12.a.readInput()
    val dist = IntGrid(map.width, map.height)
    dist.compute { Integer.MAX_VALUE }
    val (_, end) = findStartAndEnd(map)
    val modified = HashSet<IntVector>()

    // here we begin searching from the end
    dist[end] = 0
    modified.add(end)
    var least = Integer.MAX_VALUE
    while(modified.isNotEmpty()) {
        val l = ArrayList(modified)
        modified.clear()
        for (p1 in l) {
            var d = vec2(1, 0)
            val d1 = dist[p1]
            val h1 = map[p1]
            for (i in 1..4) {
                val p2 = p1 + d
                if (map.contains(p2)) {
                    val h2 = map[p2]
                    val d2 = dist[p2]
                    if (h2 >= h1-1 && d2-1 > d1) {
                        dist[p2] = d1+1
                        if (map[p2] == 0) least = min(least, d1+1)
                        modified.add(p2)
                    }
                }
                d = d.rotate(0, 1)
            }
        }
    }
    shouldBe(488, least)
}

fun findStartAndEnd(map: IntGrid): Pair<IntVector, IntVector> {
    var start : IntVector? = null
    var end : IntVector? = null
    map.forEachIndexed { p, v ->
        if (v == -14) {
            map[p] = 0
            start = p
        } else if (v == -28) {
            map[p] = 25
            end = p
        }
    }
    return Pair(start!!, end!!)
}

fun readInput(): IntGrid {
    val r = ArrayList<IntArray>()
    val lines = readInputLines(12)
    for (l in lines) {
        if (l.isBlank()) continue
        r.add(l.chars().map { c -> c - 'a'.code}.toArray())
    }
    return IntGrid(r)
}

