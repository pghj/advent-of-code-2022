package day24.a

import day18.b.copyClearForEach
import day18.b.inFourDirections
import readInputLines
import shouldBe
import util.IntVector
import vec

fun main() {
    val map = read()
    val start = vec(1,0)
    val end = map.bbox()[1]
    val t = calculate(map, 0, start, end)
    shouldBe(230, t)
}

fun calculate(map: Map, time: Int, start: IntVector, end: IntVector) : Int {

    val w = map.width()
    val h = map.height()-2

    val reachable = HashSet<IntVector>()
    reachable.add(start)
    var t = time
    do {
        t++
        map.clear()
        for (b in map.blizzards) {
            var p = b.initPos + b.velocity * t
            p = vec( ((p[0]-1) % w + w) % w + 1, ((p[1]-1) % h + h) % h + 1 )
            map.grid[p] = '*'
        }
        copyClearForEach(reachable) { p ->
            if (map.grid[p] == '.') reachable.add(p)
            inFourDirections { u ->
                val q = p + u
                if (map.grid[q] == '.') reachable.add(q)
            }
        }
    } while (!reachable.contains(end))
    return t
}

data class Blizzard(
    val initPos : IntVector,
    val velocity : IntVector,
)

class Map(
    val grid: HashMap<IntVector, Char>
) {
    val blizzards = ArrayList<Blizzard>()

    init {
        grid.forEach { (k,c) ->
            val v = when(c) {
                '<' -> vec(-1,0)
                '>' -> vec(1,0)
                '^' -> vec(0,-1)
                'v' -> vec(0,1)
                else -> null
            }
            if (v != null) blizzards.add(Blizzard(k, v))
        }
        clear()
    }

    fun clear() {
        grid.iterator().forEach { it.setValue('.') }
    }

    fun print() {
        val b = bbox()
        for (y in b[0][1]..b[1][1]) {
            for (x in b[0][0]..b[1][0])
                print(grid[vec(x, y)] ?: ' ')
            print('\n')
        }
        println('\n')
    }

    fun bbox(): Array<IntVector> {
        val x1 = grid.keys.minOf { it[0] }
        val y1 = grid.keys.minOf { it[1] }
        val x2 = grid.keys.maxOf { it[0] }
        val y2 = grid.keys.maxOf { it[1] }
        return arrayOf(vec(x1, y1), vec(x2, y2))
    }

    fun width(): Int {
        return bbox().let { it[1][0] - it[0][0] + 1 }
    }

    fun height(): Int {
        return bbox().let { it[1][1] - it[0][1] + 1 }
    }
}

fun read(): Map {
    val m = HashMap<IntVector, Char>()
    readInputLines(24)
        .filter { it.isNotBlank() }
        .forEachIndexed { y, line ->
            for (x in line.indices) {
                val c = line[x]
                if (c != '#') {
                    m[vec(x, y)] = c
                }
            }
        }
    return Map(m)
}
