package day11.a

import readInputLines
import shouldBe

fun main() {
    val monkeys = readInput()

    fun doMonkey(m: Monkey) {
        val it = m.items.iterator()
        while(it.hasNext()) {
            val item = it.next(); it.remove()
            m.activity++
            item.level = m.operation(item.level)
            item.level = item.level/3
            if (item.level % m.divTest == 0) {
                monkeys[m.ifTrue].items.add(item)
            } else {
                monkeys[m.ifFalse].items.add(item)
            }
        }
    }

    for (round in 1..20) monkeys.forEach { doMonkey(it) }

    val r = monkeys.map { it.activity }.sorted().reversed()
    val wl = r[0]*r[1]
    shouldBe(55458, wl)
}


data class Item(
    var level: Int
)

data class Monkey(
    val items : ArrayList<Item>,
    val operation : (Int) -> Int,
    val divTest : Int,
    val ifTrue : Int,
    val ifFalse : Int,
) {
    var activity: Int = 0
}

fun readInput(): ArrayList<Monkey> {
    val r = ArrayList<Monkey>()
    val it = readInputLines(11).iterator()
    while (it.hasNext()) {
        it.next() // skip Monkey

        var s = it.next()
        val items = ArrayList(s.substring(18).split(", ").map { i -> Item(i.toInt()) })
        s = it.next()
        val op = s.substring(23, 24)
        val w = s.substring(25)

        val oper: (Int, Int)->Int = when(op) {
            "*" -> { a,b -> a*b  }
            "+" -> { a,b -> a+b }
            else -> throw RuntimeException()
        }
        val oper2: (Int)->Int = if (w == "old")
            {{old:Int->oper(old, old) }}
        else
            {{old:Int->oper(old, w.toInt())}}

        s = it.next()
        val divTest = s.substring(21).toInt()
        s = it.next()
        val ifTrue = s.substring(29).toInt()
        s = it.next()
        val ifFalse = s.substring(30).toInt()
        if (it.hasNext()) it.next() // empty line

        val m = Monkey(items, oper2, divTest, ifTrue, ifFalse)
        r.add(m)
    }
    return r
}
