package day16.b

import day16.a.Valve
import day16.a.read
import day16.a.simplifyGraph
import shouldBe
import kotlin.math.max

fun main() {
    val graph = simplifyGraph(read())

    val start = graph["AA"]!!.value
    var best = 0

    // The sum of the rates of unused valves is tracked to estimate the best case of the remaining valve potential.
    // If opening all remaining valves in the next minute, and considering their output for the remaining
    // duration, will not add up to a better result than the current best, then all efforts in the
    // current search path are abandoned.
    var unused = graph.nodes().map { it.value.rate }.sum()

    fun stepElephant(v: Valve, t: Int, sum: Int) {
        if (sum + (25-t) * unused <= best) return
        for (vtx in graph[v.key]!!.connected) {
            val len = vtx.distanceFrom(v.key)
            val dst = vtx.other(v.key).value
            if (!dst.used && dst.rate != 0) {
                if (t + 1 + len < 26) {
                    dst.used = true
                    unused -= dst.rate
                    val s = sum + (26 - t - len) * dst.rate
                    best = max(best, s)
                    stepElephant(dst, t + 1 + len, s)
                    unused += dst.rate
                    dst.used = false
                }
            }
        }
    }

    fun stepElf(v: Valve, t: Int, sum: Int) {
        stepElephant(start, 1, sum)
        if (sum + (50-t) * unused <= best) return
        for (vtx in graph[v.key]!!.connected) {
            val len = vtx.distanceFrom(v.key)
            val dst = vtx.other(v.key).value
            if (!dst.used && dst.rate != 0) {
                if (t + 1 + len < 26) {
                    dst.used = true
                    unused -= dst.rate
                    val s = sum + (26 - t - len) * dst.rate
                    best = max(best, s)
                    stepElf(dst, t + 1 + len, s)
                    unused += dst.rate
                    dst.used = false
                }
            }
        }
    }

    stepElf(start, 1, 0)
    shouldBe(3015, best)
}
