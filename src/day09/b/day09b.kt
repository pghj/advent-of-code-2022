package day09.b

import day09.a.readInput
import util.IntVector
import shouldBe
import vec2
import kotlin.math.abs

fun main() {
    val moves = readInput()
    val snake = Array(10) { vec2(0,0) }
    val tailVisited = HashSet<IntVector>()
    tailVisited.add(snake[9])
    for (m in moves) {
        val (dir, n) = m
        for (step in 1..n) {
            snake[0] += dir
            for (j in 1 .. 9) {
                var d = snake[j-1] - snake[j]
                if (abs(d[0]) == 2 || abs(d[1]) == 2)
                    d = d.map { if (abs(it) == 2) it/2 else 0 }
                snake[j] = snake[j-1] - d
            }
            tailVisited.add(snake[9])
        }
    }
    shouldBe(2593, tailVisited.size)
}
