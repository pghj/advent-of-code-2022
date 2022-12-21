package day21.a

import readInputLines
import shouldBe


fun main() {
    val r = read()
    val answer = (evaluate(r) as Num).value
    shouldBe(63119856257960, answer)
}

fun evaluate(e: Expr): Expr {
    if (e is Op) {
        val a = evaluate(e.a)
        val b = evaluate(e.b)
        if (a is Num && b is Num) {
            val n = when (e.op) {
                "*" -> a.value * b.value
                "+" -> a.value + b.value
                "-" -> a.value - b.value
                "/" -> a.value / b.value
                else -> return Op(a,b,e.op)
            }
            return Num(n)
        } else {
            return Op(a,b,e.op)
        }
    } else {
        return e
    }
}

open class Expr {
    open fun any(function: (e: Expr) -> Boolean): Boolean {
        return function(this)
    }
}

class Num(val value: Long): Expr() {
    override fun toString(): String {
        return value.toString()
    }
}

class Op(
    var a: Expr,
    val b: Expr,
    val op: String
): Expr() {
    override fun any(function: (e: Expr) -> Boolean): Boolean {
        return function(this) || a.any(function) || b.any(function)
    }
    override fun toString(): String {
        return "($a$op$b)"
    }
}

fun read(): Op {
    val sym = HashMap<String, Expr>()
    val lines = readInputLines(21).filter { it.isNotBlank() }
    val set = HashSet(lines)
    while (set.isNotEmpty()) {
        val it = set.iterator()
        while (it.hasNext()) {
            val s = it.next()
            val r = s.split(":")
            val name = r[0]
            val e = r[1].substring(1)
            if (e.matches(Regex("-?[0-9]+"))) {
                sym[name] = Num(e.toLong())
                it.remove()
            } else {
                val t = e.split(" ")
                val a = sym[t[0]]
                val b = sym[t[2]]
                val op = t[1]
                if (a != null && b != null) {
                    sym[name] = Op(a, b, op)
                    it.remove()
                }
            }
        }
    }
    return sym["root"] as Op
}
