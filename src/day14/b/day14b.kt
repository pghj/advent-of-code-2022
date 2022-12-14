package day14.b

import day14.a.read
import shouldBe
import vec2

fun main() {
    val grid = read()
    val entry = vec2(500,0)
    val bottom = grid.keys.map { it[1] }.reduce(Integer::max) + 2

    fun step(): Boolean {
        var p = entry
        time@while (true) {
            for (d in listOf(0, -1, 1)) {
                val q = p + vec2(d, 1)
                if (q[1] < bottom && !grid.containsKey(q)) {
                    p = q
                    continue@time
                }
            }
            break@time
        }
        grid[p] = '.'
        return p != entry
    }
    var n = 1
    while (step()) n++
    shouldBe(25771, n)
}
