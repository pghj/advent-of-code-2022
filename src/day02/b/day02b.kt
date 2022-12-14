package day02.b

import day02.a.calcScore
import readInputLines
import shouldBe

fun main() {

    var acc = 0
    readInputLines(2).forEach {
        val opponent = it[0] - 'A'
        val r = it[2] - 'X' - 1
        var player = opponent + r
        player = (player + 3) % 3
        acc += calcScore(opponent, player)
    }
    shouldBe(10398, acc)
}
