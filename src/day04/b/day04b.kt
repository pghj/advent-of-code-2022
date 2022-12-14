package day04.b

import readInputLines
import shouldBe
import util.IntRange

fun main() {

    var count = 0
    readInputLines(4).forEach { line ->
        val r = line.split(",").let { range ->
            Array(2) { i ->
                range[i].split("-").map(String::toInt).let { ep -> IntRange(ep[0], ep[1]) }
            }
        }
        if (r[0] intersects r[1]) count++
    }
    shouldBe(857, count)
}


