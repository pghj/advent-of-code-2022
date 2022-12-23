package day23.b

import day23.a.read
import shouldBe

fun main() {
    val map = read()
    val rounds = map.simulate(Integer.MAX_VALUE)!!
    val answer = rounds + 1
    shouldBe(1003, answer)
}

