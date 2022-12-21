package day21.b

import day21.a.Expr
import day21.a.Num
import day21.a.Op
import day21.a.evaluate
import readInputLines
import shouldBe

fun main() {
    val root = read()
    val r = solveForUnknown(root.a, root.b)
    val answer = (evaluate(r) as Num).value
    shouldBe(3006709232464L, answer)
}

/**
 * Solve f = g for an unknown variable x, where x appears only once
 *
 * For example,
 *
 *  f = ( 4 + 2 * ( x - 3 ) ) / 4
 *
 *  and
 *
 *  g = ( 32 - 2 ) * 5
 *
 *  would return
 *
 *  ( ( 32 - 2 ) * 5 * 4 - 4 ) / 2 + 3
 *
 *  which is an expression equal to x
 */
fun solveForUnknown(f: Expr, g: Expr): Expr {
    fun hasUnknown(f: Expr) = f.any { it is Var }
    fun solve(fx: Expr, c: Expr): Expr {
        if (fx is Var) return c
        fx as Op
        return if (hasUnknown(fx.a)) {
            when (fx.op) {
                "*" -> solve(fx.a, (Op(c, fx.b, "/")))
                "+" -> solve(fx.a, (Op(c, fx.b, "-")))
                "-" -> solve(fx.a, (Op(c, fx.b, "+")))
                "/" -> solve(fx.a, (Op(c, fx.b, "*")))
                else -> throw RuntimeException()
            }
        } else {
            when (fx.op) {
                "*" -> solve(fx.b, (Op(c, fx.a, "/")))
                "+" -> solve(fx.b, (Op(c, fx.a, "-")))
                "-" -> solve(fx.b, (Op(fx.a, c, "-")))
                "/" -> solve(fx.b, (Op(fx.a, c, "/")))
                else -> throw RuntimeException()
            }
        }
    }
    val (exprWithUnknown, constExpr) = if (hasUnknown(f)) Pair(f,g) else Pair(g,f)
    return solve(exprWithUnknown, constExpr)
}

class Var(val name: String): Expr() {
    override fun toString(): String {
        return name
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
            if (name == "humn") {
                sym[name] = Var("x")
                it.remove()
            } else {
                val e = r[1].substring(1)
                if (e.matches(Regex("-?[0-9]+"))) {
                    sym[name] = Num(e.toLong())
                    it.remove()
                } else {
                    val t = e.split(" ")
                    val a = sym[t[0]]
                    val b = sym[t[2]]
                    val op = if (name == "root") "=" else t[1]
                    if (a != null && b != null) {
                        sym[name] = Op(a, b, op)
                        it.remove()
                    }
                }
            }
        }
    }
    return sym["root"] as Op
}
