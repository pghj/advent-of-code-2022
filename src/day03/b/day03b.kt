package day03.b

import day03.a.itemPriority
import readInputLines
import shouldBe

fun main() {
    var acc = 0
    val group = ArrayList<Set<Char>>()
    readInputLines(3).forEach {
        val sack = it.toSet()
        group.add(sack)
        if (group.size == 3) {
            val c = group[0].intersect(group[1]).intersect(group[2])
            acc += itemPriority(c.iterator().next())
            group.clear()
        }
    }
    shouldBe(2620, acc)
}
