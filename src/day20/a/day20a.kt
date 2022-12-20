package day20.a

import readInputLines
import shouldBe

fun main() {
    val input = read()

    val reordered = ArrayList(input)
    rotateOnce(input, reordered)

    val j = reordered.indexOfFirst { it.value == 0L }
    val answer = (1..3).sumOf { reordered[(j + it * 1000) % reordered.size].value }
    shouldBe(7225, answer)
}

fun rotateOnce(order: List<Num>, reordered: ArrayList<Num>) {
    order.forEach{ n -> move(reordered, reordered.indexOf(n), n.value) }
}

fun move(list: ArrayList<Num>, idx: Int, dist: Long) {
    var d = (dist % (list.size-1)).toInt()
    if (d < 0) {
        d = -d
        val n = list[idx]
        if (d <= idx) {
            for (i in idx downTo idx - d + 1)
                list[i] = list[i - 1]
            list[idx - d] = n
        } else {
            for (i in idx until list.size - d + idx - 1)
                list[i] = list[i + 1]
            list[list.size - d + idx - 1] = n
        }
    } else if (d > 0) {
        val n = list[idx]
        if (idx + d < list.size) {
            for (i in idx until idx + d)
                list[i] = list[i + 1]
            list[idx + d] = n
        } else {
            for (i in idx downTo idx + d - list.size + 2)
                list[i] = list[i - 1]
            list[idx + d - list.size + 1] = n
        }
    }
}

class Num( var value: Long ) {
    override fun toString() = value.toString()
}

fun read(): List<Num> {
    return readInputLines(20)
        .filter { it.isNotBlank() }
        .map { line -> line.toInt() }
        .map { v -> Num(v.toLong()) }
}
