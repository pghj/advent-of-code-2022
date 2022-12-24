package day24.b

import day24.a.calculate
import day24.a.read
import shouldBe
import vec

fun main() {
    val map = read()
    val start = vec(1,0)
    val end = map.bbox()[1]

    var t : Int
    t = calculate(map, 0, start, end)
    t = calculate(map, t, end, start)
    t = calculate(map, t, start, end)

    shouldBe(713, t)
}
