package day08.a

import readInputLines
import shouldBe
import vec2

fun main() {
    val f = readInput()
    val w = f[0].size
    val h = f.size
    var visibleTrees = 0
    for (x0 in 0 until w) {
        for (y0 in 0 until h) {
            val tree = vec2(x0, y0)
            val treeHeight = f[x0][y0]
            var dir = vec2(1,0)
            directions@ for (d in 0..3) {
                var p = tree + dir
                while (p[0] in 0 until w && p[1] in 0 until h) {
                    if (f[p[0]][p[1]] >= treeHeight) {
                        dir = dir.rotate(0, 1)
                        continue@directions
                    }
                    p += dir
                }
                visibleTrees++
                break@directions
            }
        }
    }
    shouldBe(1827, visibleTrees)
}

fun readInput(): ArrayList<IntArray> {
    val r = ArrayList<IntArray>()
    readInputLines(8).forEach { line ->
        r.add(line.chars().map { c -> c - '0'.code }.toArray())
    }
    return r
}
