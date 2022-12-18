package day18.a

import readInputLines
import shouldBe
import util.IntVector
import vec

fun main() {

    val dir = listOf(
        vec(1,0,0),
        vec(0,1,0),
        vec(0,0,1),
    )

    val input = read()
    val sides = HashMap<Side, Int>()
    fun add(s: Side) {
        sides.compute(s) { _,v -> if (v == null) 1 else v+1 }
    }
    input.forEach { p ->
        for (i in 0..2) {
            add(Side(p, i))
            add(Side(p + dir[i], i))
        }
    }
    val n = sides.values.filter { it == 1 }.size
    shouldBe(4608, n)
}

data class Side(
    val point: IntVector,
    val side: Int
)

fun read(): List<IntVector> {
    return readInputLines(18)
        .filter { it.isNotBlank() }
        .map { line ->
            IntVector(line.split(",").map { it.toInt() }.toIntArray())
        }
}
