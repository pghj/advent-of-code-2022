package day22.a

import readInputLines
import shouldBe
import util.IntVector
import vec
import java.util.regex.Pattern
import kotlin.math.max

fun main() {
    val input = read()

    val top = input.map.keys.minOfOrNull { it[1] }!!
    val left = input.map.keys.filter { it[1] == top }.minOfOrNull { it[0] }!!

    var p = vec(left, top)
    var d = vec(1, 0)
    for (i in input.move) {
        when (i) {
            is Int -> {
                for (k in 1..i) {
                    var c = input.map[p + d]
                    if (c == null) {
                        val q = input.wrapAround(p, d)
                        c = input.map[q]
                        if (c == '#') break
                        p = q
                    } else {
                        if (c == '#') break
                        p += d
                    }
                    input.map[p] = directionIcon(d)
                }
            }
            is Char ->{
                input.map[p] = rotationIcon(d, i)
                when(i) {
                    'L' -> d = d.rotate(1, 0)
                    'R' -> d = d.rotate(0, 1)
                }
            }
        }
    }
    input.print()
    val answer = score(p, d)
    shouldBe(27436, answer)
}

fun score(p: IntVector, d: IntVector): Long {
    return when (d) {
        vec(1,0) -> 0L
        vec(0,1) -> 1L
        vec(-1,0) -> 2L
        vec(0,-1) -> 3L
        else -> throw RuntimeException()
    } + 1000 * (p[1]+1) + 4 * (p[0]+1)
}

class Input(
    val map: HashMap<IntVector, Char>,
    val move: ArrayList<Any>,
    val width: Int,
    val height: Int
) {
    fun print() {
        for (y in 0 until height) {
            for (x in 0 until width) {
                val q = vec(x,y)
                val c = map[q]
                print(c?:' ')
            }
            print('\n')
        }
        print('\n')
    }

    fun wrapAround(p: IntVector, d: IntVector): IntVector {
        val r = -d
        var q = p
        while (map.containsKey(q + r)) q += r
        return q
    }
}

fun read(): Input {
    val lines = readInputLines(22)
    val instr = lines.last()
    val m = lines.subList(0, lines.size-1).filter { it.isNotBlank() }
    val map = HashMap<IntVector, Char>()
    var p = vec(0, 0)
    var w = 0
    var h = 0
    m.forEach { row ->
        var q = p
        for (c in row) {
            if (c == '.' || c == '#') map[q] = c
            q += vec(1,0)
            w = max(w, q[0])
            h = max(h, q[1])
        }
        p += vec(0,1)
    }
    val t = instr.split(Pattern.compile("[0-9]+")).filter { it.isNotBlank() }.map { it[0] }
    val n = instr.split(Pattern.compile("[RL]")).map { it.toInt() }
    val moves = ArrayList<Any>()
    for (i in t.indices) {
        moves.add(n[i])
        moves.add(t[i])
    }
    moves.add(n.last())
    return Input(map, moves, w+1, h+1)
}

fun directionIcon(d: IntVector) : Char = if (d[0] == 0) '┃' else '━'

fun rotationIcon(d: IntVector, t: Char) : Char {
    return when (if (t == 'R') d.rotate(1, 0) else d) {
        vec(1, 0) -> '┛'
        vec(-1, 0) -> '┏'
        vec(0, -1) -> '┓'
        vec(0, 1) -> '┗'
        else -> '?'
    }
}