package day01.b

import readInputLines
import shouldBe

fun main() {

    val largest = ArrayList<Long>()
    fun addSum(value: Long) {
        if (largest.size < 3) {
            largest.add(value)
        } else if (value > largest[0]) {
            largest[0] = value
        }
        largest.sort()
    }

    var acc = 0L
    readInputLines(1).forEach {
        if (it.isBlank()) {
            addSum(acc)
            acc = 0
        } else {
            acc += it.toLong()
        }
    }
    addSum(acc)
    val answer = largest.sum()
    shouldBe(208567, answer)
}

