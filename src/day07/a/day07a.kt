package day07.a

import readInputLines
import shouldBe

fun main() {
    val root = readInput()
    var acc = 0
    root.treeReduce(0, Integer::sum) { dir, subTreeSize ->
        val size = subTreeSize + dir.sumFileSize()
        if (size <= 100000) acc += size
        size
    }
    shouldBe(1513699, acc)
}

class Dir (
    val parent: Dir?,
    val name: String,
) {
    private val dir = HashMap<String, Dir>()
    private var files = HashMap<String, Int>()
    fun subDir(s: String): Dir {
        return dir.computeIfAbsent(s) { Dir(this, it) }
    }
    fun addFile(name: String, size: Int) {
        files[name] = size
    }
    fun sumFileSize(): Int {
        return if (files.isEmpty()) 0 else files.values.reduce { a, b -> a+b }
    }
    fun <R> treeReduce(init: R, r: (R, R) -> R, f: (Dir, R) -> R) : R {
        var a = init
        for (d in dir.values) a = r(a, d.treeReduce(init, r, f))
        return f(this, a)
    }
}

fun readInput(): Dir {
    val root = Dir(null, "")
    var cur = root
    readInputLines(7).forEach { line ->
        if (line.startsWith("$")) {
            when(line.substring(2..3)) {
                "cd" -> {
                    val arg = line.substring(5)
                    cur = if (arg == "..") cur.parent!! else if (arg == "/") root else cur.subDir(arg)
                }
            }
        } else if (line.startsWith("dir ")) {
            // do nothing: no point in adding an empty entry
        } else {
            val (size, name) = line.split(" ").let { Pair(it[0].toInt(), it[1]) }
            // we might reasonably encounter the same file twice, so do not simply increment a size counter
            cur.addFile(name, size)
        }
    }
    return root
}
