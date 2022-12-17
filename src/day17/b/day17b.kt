package day17.b

import day17.a.*
import shouldBe
import util.IntVector
import vec2
import java.lang.Integer.max
import kotlin.math.abs

fun main() {

    val input = read()

    data class Key(
        val nextShape: Int,
        val nextGust: Int,
        val surface: List<IntVector>
    )

    data class Result(
        val initial: Key,
        val next: Key,
        val rockCount: Long,
        val gainedHeight: Int,
    )

    fun rotate45(v: IntVector): IntVector {
        val w = v.rotate(1,0)
        val r = v + w
        return r.map {if (abs(it) == 2) it /2 else it}
    }

    /**
     * This method computes the surface contour of the pile of rocks: the coordinates of
     * those rocks that are reachable by falling rocks (even if they move sideways).
     * Anything below this contour will no longer affect the outcome of the simulation,
     * and can be ignored henceforth.
     */
    fun getSurfaceContour(grid: Grid, top: Int): ArrayList<IntVector> {
        val r = ArrayList<IntVector>()
        var y = top
        while(!grid[0,y]) y--
        var p = vec2(0, y)
        var t = vec2(1,1)
        r.add(p)
        while((p+t)[0] != 7) {
            if (grid[p + t]) {
                p += t
                t = t.rotate(0,1)
                r.add(p)
                continue
            }
            t = rotate45(t)
        }
        return r
    }

    /**
     * This function will drop rocks onto an existing 'pile' of rocks, until either
     *
     *   * maxRocks is reached, or
     *   * the chamber is closed off by falling rocks, such that the bottom can no longer be reached.
     *
     * The start argument determines the
     *
     * * initial contour of the 'floor',
     * * the initial rock shape to drop, and
     * * the initial index in the repeating jet pattern pushing the rocks sideways.
     *
     * The return value of this function contains:
     *
     * * The number of rocks dropped,
     * * the new surface contour of the pile, but translated downwards such that its lowest point is at y==0;
     * * the distance the surface contour was shifted downwards,
     * * the next rock that is about to drop,
     * * the index of the next jet in the repeating pattern.
     *
     * Because the surface contour is normalized to have its lowest point at y==0, it can be used as a key for memoization.
     */
    fun dropRocks(initial: Key, maxRocks: Long): Result {
        val grid = Grid()
        initial.surface.forEach { grid[it] = true }
        val oneDown = vec2(0, -1)
        var top = highestPoint(initial.surface)
        var nextShape = initial.nextShape
        var nextJet = initial.nextGust
        var rocks = 0L
        while(true) {
            val shape = shapes[nextShape]
            val size = shapeSize[nextShape]
            nextShape = (nextShape + 1) % 5
            var p = vec2(2, top+4)
            do {
                val jet = input[nextJet].let { when(it) { '<' -> vec2(-1,0); '>' -> vec2(1,0); else -> throw RuntimeException() } }
                nextJet = (nextJet + 1) % input.length
                val collide = shape.any { grid[it+p+jet] }
                if (!collide) p += jet
                val stop = shape.any { grid[it+p+oneDown] }
                if (!stop) p += oneDown
            } while(!stop)
            top = max(top, p[1] + size[1] - 1)
            for (q in shape) grid[q+p] = true
            rocks++
            val surface = getSurfaceContour(grid, top)
            val bot = lowestPoint(surface)
            if (bot != 0 || rocks == maxRocks) {
                val normalizedSurface = surface.map { it - vec2(0, bot) }
                return Result(initial, Key(nextShape, nextJet, normalizedSurface), rocks, bot)
            }
        }
    }

    var rocks = 0L
    val rocksToObserve = 1000000000000L
    var height = 0L

    val memoization = HashMap<Key, Result>()

    fun calculateCycleProperties(start: Key): Pair<Long, Long> {
        var h = 0L
        var n = 0L
        var k = start
        do {
            val r = memoization[k]!!
            h += r.gainedHeight
            n += r.rockCount
            k = r.next
        } while (k != start)
        return Pair(n, h)
    }

    val initialFlatSurface = ArrayList<IntVector>()
    (0..6).forEach { x->initialFlatSurface.add(vec2(x,0)) }

    var next = Key(0,0, initialFlatSurface)
    // drop rocks until done, or until we hit a repeating sequence
    while (true) {
        val result = dropRocks(next, rocksToObserve - rocks)
        height += result.gainedHeight
        rocks += result.rockCount
        if (rocks == rocksToObserve) break
        memoization[next] = result
        next = result.next
        if (memoization.containsKey(next)) break
    }
    if (rocks < rocksToObserve) {
        // because of the exit condition of the previous loop, we must have entered a repeating sequence
        val (r, h) = calculateCycleProperties(next)
        val nRepeats = (rocksToObserve - rocks) / r
        rocks += r * nRepeats
        height += h * nRepeats
        // remaining rocks that did not fit entirely into the repeating sequence:
        while (rocks < rocksToObserve) {
            val result = dropRocks(next, rocksToObserve-rocks)
            height += result.gainedHeight
            rocks += result.rockCount
            next = result.next
        }
    }
    // Until now, height is only the part of the pile that became sealed of by the top layer.
    // We just need to add the height of the surface contour to get the final height.
    height += highestPoint(next.surface)
    shouldBe(1585632183915, height)
}

fun lowestPoint(surface: ArrayList<IntVector>): Int {
    return surface.minOfOrNull { it[1] }!!
}

fun highestPoint(surface: List<IntVector>): Int {
    return surface.maxOfOrNull { it[1] }!!
}
