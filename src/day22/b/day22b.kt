package day22.b

import readInputLines
import shouldBe
import util.IntVector
import vec
import java.util.regex.Pattern

fun main() {
    val input = read()

    val c = input.cube
    var s = c.faces[vec(0, 0, 1)]!!
    var p = vec(0, c.size - 1)
    var d = vec(1, 0)

    val valid = 0 until c.size
    for (i in input.move) {
        when (i) {
            is Int -> {
                for (k in 1..i) {
                    val q = p + d
                    if (!(q[0] in valid && q[1] in valid)) {
                        val (s2, q2, d2) = s.changeEdge(p, d)
                        val v = s2.map[q2]
                        if (v == '#') break
                        s = s2
                        p = q2
                        d = d2
                    } else {
                        val v = s.map[q]
                        if (v == '#') break
                        p = q
                    }
                    s.map[p] = directionIcon(d)
                }
            }
            is Char -> {
                s.map[p] = rotationIcon(d, i)
                when (i) {
                    'L' -> d = d.rotate(0, 1)
                    'R' -> d = d.rotate(1, 0)
                }
            }
        }
    }
    s.map[p] = '╳'
    c.print()
    p = input.faceToUnfoldedTransform[s.normal]!!(p)
    shouldBe(15426, score(p, d))
}

fun score(p: IntVector, d: IntVector): Long {
    return when (d) {
        vec(1,0) -> 0L
        vec(0,-1) -> 1L
        vec(-1,0) -> 2L
        vec(0,1) -> 3L
        else -> throw RuntimeException()
    } + 1000 * p[1] + 4 * p[0]
}

class Cube(
    val size: Int
) {

    val faces = HashMap<IntVector, Face>()

    inner class Face(
        val normal: IntVector,
        val xAxis: IntVector,
    ) {
        val yAxis: IntVector
            get() = normal cross xAxis
        val origin: IntVector
            get() = normal - xAxis - yAxis
        val map = HashMap<IntVector, Char>()

        fun edge(idx :Int) : IntVector {
            check(idx >= 0)
            val i = idx % 4
            val a = if (i % 2 == 0) xAxis else normal cross xAxis
            return if (idx > 1) -a else a
        }

        fun changeEdge(pos: IntVector, dir: IntVector): Triple<Face, IntVector, IntVector> {
            check(pos[0] in 0 until size && pos[1] in 0 until size)
            check(dir dot dir == 1)
            val normal0 = normal
            val normal1 = xAxis * dir[0] + yAxis * dir[1]
            val face1 = faces[normal1]!!

            var q = pos + dir
            q = vec((q[0]+size) % size, (q[1]+size) % size)
            var d = dir

            // Partially unfold:
            //   Rotate xAxis of face0, so it will lie in the plane of face1.
            //   Now we can test whether the local coordinates
            //   of these faces are rotated relative to each other.

            val xAxis0rot = xAxis.rotate(normal0, normal1)

            when (xAxis0rot dot face1.xAxis) {
                1 -> {
                    // local coordinates of faces are already aligned
                }
                -1 -> {
                    // rotate 180
                    q = q.rotate2(0,1) + vec(size-1,size-1)
                    d = d.rotate2(0,1)
                }
                0 -> {
                    // need to rotate 90 degrees, but in which direction...
                    when ((xAxis0rot cross face1.xAxis) dot normal1) {
                        1 -> {
                            // rotate 90 cw
                            q = q.rotate(1, 0) + vec(0, size-1)
                            d = d.rotate(1, 0)
                        }
                        -1 -> {
                            // rotate 90 ccw
                            q = q.rotate(0, 1) + vec(size-1, 0)
                            d = d.rotate(0, 1)
                        }
                        else -> throw RuntimeException()
                    }
                }
                else -> throw RuntimeException()
            }
            return Triple(face1, q, d)
        }

        fun print() {
            println("Face: normal: $normal / origin: $origin / xAxis: $xAxis / yAxis: $yAxis")
            for (y in size-1 downTo 0) {
                for (x in 0 until size) {
                    val c = map[vec(x,y)]!!
                    print(c)
                }
                print("\n")
            }
        }
    }

    fun addFace(normal: IntVector, xAxis: IntVector): Face {
        val s = Face(normal, xAxis)
        faces[normal] = s
        return s
    }

    fun print() {
        for (s in faces.values) {
            s.print()
            print("\n")
        }
    }

}

class Input(
    val cube: Cube,
    val move: ArrayList<Any>,
    val faceToUnfoldedTransform: HashMap<IntVector, (IntVector)->IntVector>,
)

fun read(): Input {
    val size = 50
    val lines = readInputLines(22)
    val instr = lines.last()
    val map = HashMap<IntVector, Char>()
    run {
        var p = vec(0, 0)
        val m = lines.subList(0, lines.size-1).filter { it.isNotBlank() }
        m.forEach { row ->
            var q = p
            for (c in row) {
                if (c == '.' || c == '#') map[q] = c
                q += vec(1,0)
            }
            p += vec(0,1)
        }
    }
    val moves = ArrayList<Any>()
    run {
        val t = instr.split(Pattern.compile("[0-9]+")).filter { it.isNotBlank() }.map { it[0] }
        val n = instr.split(Pattern.compile("[RL]")).map { it.toInt() }
        for (i in t.indices) {
            moves.add(n[i])
            moves.add(t[i])
        }
        moves.add(n.last())
    }

    // Note: for all coordinates during calculation we switch to positive/standard/right-handed orientation (y-axis is UP)

    // Fold map into a cube
    val cube = Cube(size)

    // We need these later to transform coordinates on the face of the cube back to the coordinates
    // in the unfolded input data.
    val faceToUnfoldedTransform = HashMap<IntVector, (IntVector)->IntVector >()

    fun addSide(pUnfolded: IntVector, sideNormal: IntVector, xAxis: IntVector): Cube.Face {
        faceToUnfoldedTransform[sideNormal] = { p -> pUnfolded + vec(0, size) + vec(p[0] + 1, -p[1]) }
        val side = cube.addFace(sideNormal, xAxis)
        for (x in 0 until size) {
            for (y in 0 until size)
                side.map[vec(x,y)] = map[vec(pUnfolded[0] + x, pUnfolded[1] + size - y - 1)]!!
        }
        return side
    }

    // Search on the four sides of a face we already have.
    fun findAllSides(pUnfolded: IntVector, face0: Cube.Face) {
        if (cube.faces.size == 6) return
        var d = vec(1, 0)
        for(edgeIdx in 0..3) {
            val q = pUnfolded + d*size
            val normal1 = face0.edge(edgeIdx)
            if (map.containsKey(q) && !cube.faces.containsKey(normal1)) {
                val xAxis1 = face0.xAxis.rotate(face0.normal, normal1)
                val s = addSide(q, normal1, xAxis1)
                findAllSides(q, s)
            }
            if (cube.faces.size == 6) break
            d = d.rotate(1,0)
        }
    }

    val p = vec(map.keys.filter { it[1] == 0 }.minOf { it[0] }, 0)
    val s = addSide(p, vec(0,0,1), vec(1,0,0))
    findAllSides(p, s)
    return Input(cube, moves, faceToUnfoldedTransform)
}

fun directionIcon(d: IntVector) : Char = if (d[0] == 0) '┃' else '━'

fun rotationIcon(d: IntVector, t: Char) : Char {
    return when (if (t == 'R') d.rotate(0, 1) else d) {
        vec(1, 0) -> '┛'
        vec(-1, 0) -> '┏'
        vec(0, 1) -> '┓'
        vec(0, -1) -> '┗'
        else -> '?'
    }
}
