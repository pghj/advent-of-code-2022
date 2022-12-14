package day10.b
import day10.a.readInput
import md5
import shouldBe

fun main() {
    val r = readInput()
    var x = 1
    var s = 0
    val sb = StringBuilder()
    fun newLine() {
        sb.append('\n')
        s = 0
    }
    fun draw() {
        sb.append(if (s in x-1 .. x+1) 'X' else '.')
    }
    for (i in r) {
        if (i == 0) {
            draw()
            s++
            if (s == 40) newLine()
        } else {
            draw()
            s++
            if (s == 40) newLine()
            draw()
            s++
            x+=i
            if (s == 40) newLine()
        }
    }
    val out = sb.toString()
    println(out) // PAPKFKEJ
    shouldBe("943c643132df0f0124228272bb6d98e7", out.md5())
}
