package day18.b

import day18.a.Side
import day18.a.read
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

/**
 * The collection will be cleared. Then the given procedure will be called
 * for each element previously in the collection.
 * The procedure may add (or re-add) elements to the collection.
 * This alternating clear-invoke process repeats until the collection remains empty.
 */
fun <T> repeatUntilEmpty(collection: MutableCollection<T>, each : (T) -> Unit) {
    val l = ArrayList<T>()
    while (collection.isNotEmpty()) {
        l.addAll(collection)
        collection.clear()
        l.forEach { each(it) }
        l.clear()
    }
}

/**
 * Create a copy of the collection, clear the collection, and invoke function for each value in the copy.
 */
inline fun <T> copyClearForEach(collection: MutableCollection<T>, each : (T) -> Unit) {
    val cpy = ArrayList(collection)
    collection.clear()
    cpy.forEach { each(it) }
}

/**
 * Invoke action for all four unit directions in 2-dimensional space.
 */
inline fun inFourDirections( action: (IntVector) -> Unit) {
    var p = vec(1,0)
    action(p)
    repeat(3) {
        p = p.rotate(0, 1)
        action(p)
    }
}

fun inBounds(min: IntVector, max: IntVector, p: IntVector): Boolean {
    for (i in 0..2)
        if (p[i] !in min[i]..max[i]) return false
    return true
}
