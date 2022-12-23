package day23.a

import readInputLines
import shouldBe
import util.IntVector
import vec

fun main() {
    val map = read()
    map.simulate(10)
    val answer = map.width() * map.height() - map.elves.size
    shouldBe(3812, answer)
}

class Ground(
    val elves: HashMap<IntVector, Elf>
) {

    fun print() {
        val b = bbox()
        for (y in b[0][1] .. b[1][1]) {
            for (x in b[0][0] .. b[1][0]) {
                print(if (elves.contains(vec(x, y))) '#' else '.')
            }
            println()
        }
        println()
    }

    fun bbox(): Array<IntVector> {
        val x1 = elves.values.minOf { it.pos[0] }
        val y1 = elves.values.minOf { it.pos[1] }
        val x2 = elves.values.maxOf { it.pos[0] }
        val y2 = elves.values.maxOf { it.pos[1] }
        return arrayOf(vec(x1,y1), vec(x2,y2))
    }

    fun width(): Int {
        return bbox().let { it[1][0] - it[0][0] + 1 }
    }

    fun height(): Int {
        return bbox().let { it[1][1] - it[0][1] + 1 }
    }

    private fun hasNeighbour(e: Elf): Boolean {
        val pos = e.pos
        var d = vec(1,0)
        for (j in 0..3) {
            if (elves.containsKey(pos+d)) return true
            d = d.rotate(0,1)
        }
        d = vec(1,1)
        for (j in 0..3) {
            if (elves.containsKey(pos+d)) return true
            d = d.rotate(0,1)
        }
        return false
    }

    private fun isDirectionFree(p: IntVector, d: IntVector): Boolean {
        val l = d.rotate(0,1)
        val r = d.rotate(1,0)
        var f = d + l
        for (k in 0..2) {
            if (elves.containsKey(p + f)) return false
            f += r
        }
        return true
    }

    fun simulate(maxRounds: Int): Int? {
        val listOfAllElves = elves.values.toList()
        val loop = listOf(vec(0,-1), vec(0,1), vec(-1,0), vec(1,0))
        var loopIdx = 0
        val dst = HashMap<IntVector, Elf>()
        for (i in 1..maxRounds) {
            // decide where to go
            var wouldMove = false
            for (e in listOfAllElves) {
                e.goto = null
                if (!hasNeighbour(e)) continue
                wouldMove = true
                for (j in 0..3) {
                    val d = loop[(loopIdx + j) % 4]
                    if (isDirectionFree(e.pos, d)) {
                        e.goto = e.pos + d
                        break
                    }
                }
            }
            if (!wouldMove) return i-1
            // detect collisions
            dst.clear()
            for (e in listOfAllElves) {
                val g = e.goto
                if (g != null) {
                    val e2 = dst[e.goto]
                    if (e2 != null) {
                        e2.goto = null
                        e.goto = null
                    } else {
                        dst[g] = e
                    }
                }
            }
            // apply movements
            for (e in listOfAllElves) {
                val g = e.goto
                if (g != null) {
                    elves.remove(e.pos)
                    elves[g] = e
                    e.pos = g
                    e.goto = null
                }
            }
            loopIdx = (loopIdx+1)%4
        }
        return null
    }
}

class Elf(
    var pos : IntVector,
    var goto : IntVector? = null,
)

typealias Input = Ground


fun read(): Input {
    val map = HashMap<IntVector, Elf>()
    readInputLines(23)
        .filter { it.isNotBlank() }
        .forEachIndexed { i, s ->
            for (j in s.indices) {
                val p = vec(j + 1, i + 1)
                if (s[j] == '#') map[p] = Elf(p)
            }
        }
    return Ground(map)
}
