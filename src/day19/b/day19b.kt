package day19.b

import day19.a.read
import day19.a.simulate
import shouldBe

fun main() {
    val input = read().subList(0, 3)
    var answer = 1
    input.forEach { answer *= simulate(it, 32) }
    shouldBe(5800, answer)
}
