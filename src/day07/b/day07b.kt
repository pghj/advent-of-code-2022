package day07.b

import day07.a.readInput
import shouldBe
import java.lang.Integer.min

fun main() {
    val root = readInput()
    val rootSize = root.treeReduce(0, Integer::sum) { dir, subTreeSize -> subTreeSize + dir.sumFileSize() }
    val needToFree = 30000000 - (70000000 - rootSize)
    var smallest = Integer.MAX_VALUE
    root.treeReduce(0, Integer::sum) { dir, subTreeSize ->
        val size = subTreeSize + dir.sumFileSize()
        if (needToFree <= size) smallest = min(smallest, size)
        size
    }
    shouldBe(7991939, smallest)
}
