package day11.b

import readInputLines
import shouldBe

fun main() {
    val monkeys = readInput()

    fun doMonkey(m: Monkey) {
        val it = m.items.iterator()
        while(it.hasNext()) {
            val item = it.next(); it.remove()
            m.activity++
            item.rem = m.operation(item.rem)
            if (item.rem[m.divTest] == 0) {
                monkeys[m.ifTrue].items.add(item)
            } else {
                monkeys[m.ifFalse].items.add(item)
            }
        }
    }

    for (round in 1..10000) monkeys.forEach { doMonkey(it) }

    val r = monkeys.map { it.activity }.sorted().reversed()
    val wl = 1L*r[0]*r[1]
    shouldBe(14508081294, wl)
}

data class Remainders(
    val m: HashMap<Int, Int> = HashMap()
) {
    constructor(other: Remainders) : this(HashMap(other.m))

    constructor(value: Int) : this() {
        for (div in listOf(2,3,5,7,11,13,17,19)) { this[div] = value % div }
    }

    operator fun plus(that: Remainders): Remainders {
        val r = Remainders(this)
        that.m.forEach { (k, v) -> r[k] = (r[k] + v) % k }
        return r
    }

    operator fun times(that: Remainders): Remainders {
        val r = Remainders()
        m.forEach { (k, v) -> r[k] = (that[k] * v) % k }
        return r
    }

    operator fun get(div: Int) : Int {
        return m[div]?:0
    }

    operator fun set(div: Int, rem: Int) {
        if (rem == 0) m.remove(div)
        else m[div] = rem
    }
}

data class Item(
    var rem: Remainders = Remainders()
)

data class Monkey(
    val items : ArrayList<Item>,
    val operation : (Remainders) -> Remainders,
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
        val items = ArrayList(s.substring(18).split(", ").map { i -> Item(Remainders(i.toInt())) })
        s = it.next()
        val op = s.substring(23, 24)
        val w = s.substring(25)

        val oper: (Remainders, Remainders)->Remainders = when(op) {
            "*" -> { a,b -> a*b  }
            "+" -> { a,b -> a+b }
            else -> throw RuntimeException()
        }
        val oper2: (Remainders)->Remainders = if (w == "old") {
            {old:Remainders->oper(old, old)}
        } else {
            {old:Remainders->oper(old, Remainders(w.toInt()))}
        }
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
