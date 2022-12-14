package day02.a

import readInputLines
import shouldBe

fun main() {
    var acc = 0
    readInputLines(2).forEach {
        val opponent = it[0] - 'A'
        val player = it[2] - 'X'
        acc += calcScore(opponent, player)
    }
    shouldBe(13009, acc)
}

fun calcScore(opponent: Int, player: Int): Int {
    return (isWin(opponent, player) +1) * 3 + handScore(player)
}

fun handScore(hand: Int): Int {
    return hand + 1
}

fun isWin(opponent: Int, player: Int): Int {
    if (player == opponent) return 0
    val r = if (player > opponent) 1 else -1
    return if (player*opponent == 0 && player+opponent==2) -r else r
}
