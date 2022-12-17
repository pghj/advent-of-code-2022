package day17.a

import readInputLines
import shouldBe
import util.IntVector
import vec2
import java.lang.Integer.max
import java.util.*

val shapes = listOf(
    listOf(vec2(0,0),vec2(1,0),vec2(2,0),vec2(3,0)),
    listOf(vec2(1,0),vec2(0,1),vec2(1,1),vec2(2,1), vec2(1,2)),
    listOf(vec2(2,2),vec2(2,1),vec2(2,0),vec2(1,0), vec2(0,0)),
    listOf(vec2(0,0),vec2(0,1),vec2(0,2),vec2(0,3)),
    listOf(vec2(0,0),vec2(1,0),vec2(0,1),vec2(1,1)),
)
val shapeSize = listOf(vec2(4,1), vec2(3,3), vec2(3,3),vec2(1,4), vec2(2,2))

fun main() {

    val grid = Grid()
    for (x in 0..6) grid[x,0] = true
    val input = read()

    val oneDown = vec2(0,-1)
    var top = 0
    var shapeIdx = 0
    var jetIdx = 0
    for (i in 0 until 2022) {
        //add shape
        val shape = shapes[shapeIdx]
        val size = shapeSize[shapeIdx]
        var p = vec2(2, top+4)
        do {
            val jet = input[jetIdx].let { when(it) { '<' -> vec2(-1,0); '>' -> vec2(1,0); else -> throw RuntimeException() } }
            jetIdx = (jetIdx + 1) % input.length
            val collide = shape.any { grid[it+p+jet] }
            if (!collide) p += jet
            val stop = shape.any { grid[it+p+oneDown] }
            if (!stop) p += oneDown
        } while(!stop)
        top = max(top, p[1] + size[1] - 1)
        for (q in shape) grid[q+p] = true
        shapeIdx = (shapeIdx + 1) % 5
    }
    shouldBe(3232, top)
}

class Grid {
    private val grid = BitSet()
    operator fun get(p: IntVector) : Boolean {
        return get(p[0], p[1])
    }
    operator fun get(x: Int, y: Int) : Boolean {
        if (x !in 0..6) return true
        val i = x + (y * 7)
        return grid[i]
    }
    operator fun set(p: IntVector, b: Boolean) {
        set(p[0], p[1], b)
    }
    operator fun set(x: Int, y: Int, b: Boolean) {
        val i = x + (y * 7)
        grid[i] = b
    }
}

fun show(grid: Grid, top: Int) {
    for (y in top downTo  0) {
        if (y == 0) {
            println("+-------+")
        } else {
            print('|')
            for (x in 0..6)
                print(if (grid[x, y]) '#' else ' ')
            println('|')
        }
    }
}

fun read(): String {
    return readInputLines(17)[0]

}
