package day20.b

import day20.a.read
import day20.a.rotateOnce
import shouldBe

fun main() {
    val input = read()

    input.forEach { it.value *= 811589153 }

    val reordered = ArrayList(input)
    repeat(10) { rotateOnce(input, reordered) }

    val j = reordered.indexOfFirst { it.value == 0L }
    val answer = (1..3).sumOf { reordered[(j + it * 1000) % reordered.size].value }
    shouldBe(548634267428, answer)
}
