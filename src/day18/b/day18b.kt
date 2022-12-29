package day18.b

import day18.a.Side
import day18.a.read
import inBounds
import repeatUntilEmpty
import shouldBe
import util.IntVector
import vec

fun main() {

    val dir = listOf(
        vec(1,0,0),
        vec(0,1,0),
        vec(0,0,1),
    )

    val cubes = read()

    // Bounding box with an added margin of 1 on all sides
    val min = IntVector(3) { i -> cubes.minOf { it[i] }-1 }
    val max = IntVector(3) { i -> cubes.maxOf { it[i] }+1 }

    val outsideAir = HashSet<IntVector>()

    for (x in min[0] .. max[0]) {
        for (y in min[1]..max[1]) {
            for (z in min[2]..max[2]) {
                if (
                    x == min[0] || x == max[0] ||
                    y == min[1] || y == max[1] ||
                    z == min[2] || z == max[2]
                ) {
                    // There are no cubes on the surface of the bounding box, so it must be air.
                    outsideAir.add(vec(x, y, z))
                }
            }
        }
    }

    // Find all air in contact with outside air, that is not blocked by cubes.
    val w = HashSet(outsideAir)
    repeatUntilEmpty(w) { p ->
        dir.forEach { d ->
            listOf(-1,1)
                .map { s -> d*s + p } // all neighbouring cells
                .filter { inBounds(min, max, it) } // that are within bounds
                .filter { !cubes.contains(it) } // and not already occupied by cube
                .filter { !outsideAir.contains(it) } // and not already found
                .forEach {
                    outsideAir.add(it) // add
                    w.add(it) // and use as starting point to continue search
                }
        }
    }

    // Collect all cell sides of the outside air.
    val outsideAirSides = HashSet<Side>()
    outsideAir.forEach { p ->
        (0..2).forEach { i ->
            listOf( Side(p, i), Side(p + dir[i], i) ).forEach { airSide ->
                outsideAirSides.add(airSide)
            }
        }
    }

    // Count all cube sides in contact with outside air sides.
    var n = 0
    cubes.forEach { p ->
        (0..2).forEach { i ->
            listOf( Side(p, i), Side(p + dir[i], i) ).forEach { cubeSide ->
                if (outsideAirSides.contains(cubeSide)) n++
            }
        }
    }

    shouldBe(2652, n)
}

