package day05.a

import readInputLines
import shouldBe

fun main() {
    val lines = readInputLines(5)
    val i = lines.indexOfFirst { it.isBlank() }
    val stacks = readInitialStacks(lines.subList(0, i))
    val instr = lines.subList(i+1, lines.size)
    for (j in instr) apply(decodeInstruction(j), stacks)
    val top = stacks.map { it.last() }.joinToString("")
    shouldBe("VJSFHWGFT", top)
}

private fun apply(job: IntArray, stacks: Array<ArrayList<Char>>) {
    val n = job[0]
    val from = stacks[job[1]-1]
    val to = stacks[job[2]-1]
    for (i in 0 until n)
        to.add(from.removeLast())
}

fun decodeInstruction(str: String): IntArray {
    return str.split(" ").filterIndexed { i, _ -> i % 2 == 1 }.map(String::toInt).toIntArray()
}

fun readInitialStacks(line: List<String>): Array<ArrayList<Char>> {
    // ignore last line with indices
    val idxBottom = line.size - 2
    val nStacks = (line[idxBottom].length + 1) / 4
    val stack = Array<ArrayList<Char>>(nStacks) { ArrayList() }
    for (j in idxBottom downTo 0) {
        val l = line[j]
        for (i in 0 until nStacks) {
            val c = l[4*i+1]
            if (c != ' ') stack[i].add(c)
        }
    }
    return stack
}
