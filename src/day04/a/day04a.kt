package day04.a

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
        if (r[1].contains(r[0]) || r[0].contains(r[1])) count++
    }
    shouldBe(485, count)
}

