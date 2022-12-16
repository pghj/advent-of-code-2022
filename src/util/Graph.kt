package util

import java.util.Collections

class Graph<K, N> {

    inner class Node(
        val key: K,
        val value: N
    ) {
        val connected: ArrayList<Vertex> = ArrayList()
    }

    inner class Vertex(
        val a: Node,
        val b: Node,
        val aToB: Int,
        val bToA: Int,
    ) {
        fun other(n: Node): Node = if (n == a) b else a
        fun other(k: K): Node = if (k == a.key) b else a
        fun distanceFrom(k: K) = if (k == a.key) aToB else bToA
        fun distanceTo(k: K) = if (k == a.key) bToA else aToB
    }

    private val nodeMap = HashMap<K, Node>()
    private val vertexes = ArrayList<Vertex>()

    fun nodes() : Collection<Node> {
        return Collections.unmodifiableCollection(nodeMap.values)
    }

    fun add(key: K, value: N) {
        if (nodeMap.containsKey(key)) throw IllegalArgumentException("duplicate key")
        val n = Node(key, value)
        nodeMap[key] = n
    }

    operator fun get(key: K): Node? {
        return nodeMap[key]
    }

    fun connect(key0: K, key1: K, distance: Int, reversed: Int = distance) {
        val a = nodeMap[key0]!!
        val b = nodeMap[key1]!!
        val vtx = Vertex(a, b, distance, reversed)
        a.connected.add(vtx)
        b.connected.add(vtx)
        vertexes.add(vtx)
    }

    /** Calculate the shortest distance needed from the given node to each other node.
     * Entries that are missing in the returned map are disconnected from the given node.
     * */
    fun calculateLeastDistanceTable(key: K) : HashMap<K, Int> {
        val a = nodeMap[key]!!
        val minDist = HashMap<K, Int>()
        val mod = HashSet<Node>()
        val l = ArrayList<Node>()
        minDist[key] = 0
        mod.add(a)
        while(mod.isNotEmpty()) {
            l.clear()
            l.addAll(mod)
            mod.clear()
            l.forEach { n ->
                for(v in n.connected) {
                    val d0 = minDist[n.key]!!
                    val m = v.other(a)
                    val d1 = minDist[m.key]
                    if ((d1 != null && d0+v.aToB < d1) || d1 == null) {
                        mod.add(m)
                        minDist[m.key] = d0+v.aToB
                    }
                }
            }
        }
        return minDist
    }

}