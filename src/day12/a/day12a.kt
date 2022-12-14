package day12.a

import readInputLines
import shouldBe
import util.IntGrid
import util.IntVector
import vec2

fun main() {
    val map = readInput()
    val dist = IntGrid(map.width, map.height)
    dist.compute { Integer.MAX_VALUE }
    val (start, end) = findStartAndEnd(map)
    val modified = HashSet<IntVector>()

    // dist will contain the shortest route to each location
    // it will be iteratively updated from the previous updated
    // coordinates, until no further improvements can be made

    dist[start] = 0
    modified.add(start)

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
                    if (h2 <= h1+1 && d2-1 > d1) {
                        dist[p2] = d1+1
                        modified.add(p2)
                    }
                }
                d = d.rotate(0, 1)
            }
        }
    }

    val least = dist[end]
    shouldBe(490, least)
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
