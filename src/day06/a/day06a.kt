package day06.a

import readInputLines
import shouldBe

fun main() {
    val input = readInputLines(6)[0]
    val n = findStartOfPacket(input)
    shouldBe(1582, n)
}

private fun findStartOfPacket(input: String): Int {
    // Using a cyclic buffer. Order is not important.
    val buf = CharArray(4)
    for (i in 0..2) buf[i] = input[i]
    for (i in 3 until input.length) {
        buf[i % 4] = input[i]
        val n = countUnique(buf)
        if (n == 4) return i+1
    }
    return -1
}

private fun countUnique(buf: CharArray): Int {
    var n = 4
    for (i in 1 until 4) {
        for (j in 0 until i) {
            // Worst case, this inner block only runs 6 times because of the small buffer
            if (buf[i] == buf[j]) {
                n--
                break
            }
        }
    }
    return n
}
