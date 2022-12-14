package day10.a

import readInputLines
import shouldBe

fun main() {
    val r = readInput()
    var x = 1
    var s = 1
    var out = 0
    for (i in r) {
        if (i == 0) {
            s++
            if ((s-20)%40==0) out+=s*x
        } else {
            s++
            if ((s-20)%40==0) out+=s*x
            s++
            x+=i
            if ((s-20)%40==0) out+=s*x
        }
    }
    shouldBe(14060, out)
}

fun readInput(): ArrayList<Int> {
    val r = ArrayList<Int>()
    readInputLines(10).forEach { line ->
        if (line == "noop") r.add(0)
        else r.add(line.substring(5).toInt())
    }
    return r
}
