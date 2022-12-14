package day08.b

import day08.a.readInput
import shouldBe
import vec2
import java.lang.Integer.max

fun main() {
    val f = readInput()
    val w = f[0].size
    val h = f.size
    var bestScore = 0
    for (x0 in 0 until w) {
        for (y0 in 0 until h) {
            val tree = vec2(x0, y0)
            val treeHeight = f[x0][y0]
            var dir = vec2(1,0)
            var score = 1
            for (d in 0..3) {
                var p = tree + dir
                var visible = 0
                while (p[0] in 0 until w && p[1] in 0 until h) {
                    visible++
                    if (f[p[0]][p[1]] >= treeHeight) break
                    p += dir
                }
                score *= visible
                dir = dir.rotate(0,1)
            }
            bestScore = max(bestScore, score)
        }
    }
    shouldBe(335580, bestScore)
}
