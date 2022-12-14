package day14.a

import readInputLines
import shouldBe
import vec2
import util.IntVector
import java.lang.Integer.max
import java.lang.Integer.min
import kotlin.math.abs

typealias Grid = HashMap<IntVector, Char>

fun main() {
    val grid = read()
    val entry = vec2(500,0)
    val bottom = grid.keys.map { it[1] }.reduce(Integer::max)
    fun step(): Boolean {
        var p = entry
        time@while (true) {
            for (d in listOf(0, -1, 1)) {
                val q = p + vec2(d, 1)
                if (q[1] > bottom) return true
                if (!grid.containsKey(q)) {
                    p = q
                    continue@time
                }
            }
            break@time
        }
        grid[p] = '.'
        return false
    }

    var n = 0
    while (!step()) n++
    shouldBe(1003, n)
}

fun show(grid: Grid) {
    val bb0 = grid.keys.reduce{  a,b -> vec2(min(a[0],b[0]), min(a[1],b[1])) }
    val bb1 = grid.keys.reduce{  a,b -> vec2(max(a[0],b[0]), max(a[1],b[1])) }
    println()
    for (y in bb0[1]..bb1[1]) {
        for (x in bb0[0]..bb1[0])
            print(grid[vec2(x, y)] ?: ' ')
        println()
    }
}

fun read(): Grid {
    val r = readInputLines(14).map { s -> s.split(" -> ").map { IntVector.parse(it) } }
    val g = Grid()
    r.forEach { rock ->
        val it = rock.iterator()
        var a = it.next()
        while(it.hasNext()) {
            val b = it.next()
            val d = (b-a).map { if (it == 0) 0 else it / abs(it) }
            var c = a
            g[c] = '#'
            while (c != b) {
                c += d
                g[c] = '#'
            }
            a = b
        }
    }
    return g
}
