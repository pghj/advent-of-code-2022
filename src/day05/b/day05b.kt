package day05.b

import day05.a.decodeInstruction
import day05.a.readInitialStacks
import readInputLines
import shouldBe

fun main() {

    val lines = readInputLines(5)
    val i = lines.indexOfFirst { it.isBlank() }
    val stacks = readInitialStacks(lines.subList(0, i))
    val instr = lines.subList(i+1, lines.size)
    for (j in instr) apply(decodeInstruction(j), stacks)
    val top = stacks.map { it.last() }.joinToString("")
    shouldBe("LCTQFBVZV", top)
}

private fun apply(job: IntArray, stacks: Array<ArrayList<Char>>) {
    val n = job[0]
    val from = stacks[job[1]-1]
    val to = stacks[job[2]-1]
    from.subList(from.size - n, from.size).let { moveThis ->
        moveThis.forEach { to.add(it) }
        moveThis.clear()
    }
}
