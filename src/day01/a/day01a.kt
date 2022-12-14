package day01.a

import readInputLines
import shouldBe
import java.lang.Long.max

fun main() {
    var largest = 0L
    var acc = 0L
    readInputLines(1).forEach {
        if (it.isBlank()) {
            largest = max(largest, acc)
            acc = 0
        } else {
            val n = it.toLong()
            acc += n
        }
    }
    largest = max(largest, acc)
    shouldBe(70509, largest)
}