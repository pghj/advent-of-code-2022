package day16.a

import readInputLines
import shouldBe
import util.Graph
import java.util.regex.Pattern
import kotlin.math.max

fun main() {
    val graph = simplifyGraph(read())

    var best = 0
    fun step(v: Valve, t: Int, sum: Int) {
        for (vtx in graph[v.key]!!.connected) {
            val len = vtx.distanceFrom(v.key)
            val dst = vtx.other(v.key).value
            if (!dst.used && dst.rate != 0) {
                if (t + 1 + len < 30) {
                    dst.used = true
                    val s = sum + (30 - t - len) * dst.rate
                    best = max(best, s)
                    step(dst, t + 1 + len, s)
                    dst.used = false
                }
            }
        }
    }

    val start = graph["AA"]!!.value
    step(start, 1, 0)
    shouldBe(2250, best)
}

/**
 * Many Valves have rate == 0, and these are eliminated from the graph.
 * Only the "AA" Valve is preserved, but the 'used' flag is set such that it will be ignored.
 * Every remaining Valve will be connected with every other Valve by a vertex with as its length
 * the shortest distance that can be found by traveling the original graph.
 * In the subsequent search, this will prevent repeatedly evaluating the same Valve order through suboptimal paths.
 */
fun simplifyGraph(graph: Graph<String, Valve>): Graph<String, Valve> {
    val simplified = Graph<String, Valve>()

    graph.nodes()
        .filter { it.value.rate != 0 || it.key == "AA" }
        .forEach { simplified.add(it.key, it.value) }

    simplified.nodes().toList().let { list ->
        for ((i, a) in list.withIndex()) {
            val d = graph.calculateLeastDistanceTable(a.key)
            for (b in list.subList(i + 1, list.size)) {
                val p = d[b.key]!!
                simplified.connect(a.key, b.key, p)
            }
        }
    }
    simplified["AA"]!!.value.used = true
    return simplified
}

class Valve(
    val key: String,
    val rate: Int,
) {
    override fun toString(): String { return key }
    var used = false
}

fun read(): Graph<String, Valve> {
    val g = Graph<String, Valve>()
    val connectTo = HashMap<String, ArrayList<String>>()
    readInputLines(16).forEach { line ->
        val fr = line.split(Pattern.compile("[^0-9]+")).filter { it.isNotBlank() }[0].toInt()
        val va = line.substring(1).split(Pattern.compile("[^A-Z]")).filter { it.isNotBlank() }
        val valve = Valve(va[0], fr)
        g.add(valve.key, valve)
        connectTo[valve.key] = ArrayList(va.subList(1, va.size))
    }
    g.nodes().forEach { a ->
        for (k in connectTo[a.key]!!) {
            val b = g[k]!!
            g.connect(a.key, b.key, 1)
        }
    }
    return g
}
