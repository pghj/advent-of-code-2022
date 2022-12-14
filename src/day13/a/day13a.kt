package day13.a

import readInputLines
import shouldBe

fun main() {
    val msg = read()

    var r = 0
    var i = 0
    for (p in msg) {
        i++
        val (m0, m1) = p
        if (m0 < m1) r += i
    }
    shouldBe(6187, r)
}

data class Message(
    val list : List<*>
) : Comparable<Message> {
    constructor(v: Int): this(listOf(v))

    override fun toString(): String {
        return list.toString()
    }

    override operator fun compareTo(other: Message): Int {
        val it0 = this.list.iterator()
        val it1 = other.list.iterator()
        while (it0.hasNext() && it1.hasNext()) {
            var v0 = it0.next()
            var v1 = it1.next()
            var d : Int
            if (v0 is Int && v1 is Int) {
                d = v0 - v1
            } else {
                if (v0 is Int) v0 = Message(v0)
                if (v1 is Int) v1 = Message(v1)
                v0 as Message
                v1 as Message
                d = v0.compareTo(v1)
            }
            if (d != 0) return d
        }
        if (it0.hasNext()) return 1
        if (it1.hasNext()) return -1
        return 0
    }
}

fun read(): ArrayList<Pair<Message, Message>> {
    val r = ArrayList<Pair<Message, Message>>()
    val lines = readInputLines(13)
    val it = lines.iterator()
    while(it.hasNext()) {
        val s0 = it.next()
        val s1 = it.next()
        val m0 = parse(s0, 0)
        val m1 = parse(s1, 0)
        r.add(Pair(m0.second,m1.second))
        if (it.hasNext()) it.next()
    }
    return r
}

fun parse(s: String, start: Int): Pair<Int, Message> {
    val l = ArrayList<Any>()
    var i = start + 1
    while (true) {
        when (s[i]) {
            '[' -> {
                val p = parse(s, i)
                i = p.first + 1
                l.add(p.second)
            }
            ']' -> return Pair(i, Message(l))
            ',' -> i++
            else -> {
                val j = i
                while (s[i] in '0'..'9') i++
                val v = s.substring(j, i).toInt()
                l.add(v)
            }
        }
    }
}
