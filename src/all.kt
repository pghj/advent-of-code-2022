
fun main() {

    class Result(
        val day: Int,
        val part: String,
        val time: Long
    )

    val results = ArrayList<Result>()

    fun d(day: Int, part: String, job: ()->Unit) {
        println("\nDay $day.$part\n========\n")
        val t0 = System.currentTimeMillis()
        job()
        val d = System.currentTimeMillis() - t0
        println("\ntime: ${d}ms\n")
        results.add(Result(day, part, d))
    }
    
    d(1, "a") { day01.a.main() }
    d(1, "b") { day01.b.main() }
    d(2, "a") { day02.a.main() }
    d(2, "b") { day02.b.main() }
    d(3, "a") { day03.a.main() }
    d(3, "b") { day03.b.main() }
    d(4, "a") { day04.a.main() }
    d(4, "b") { day04.b.main() }
    d(5, "a") { day05.a.main() }
    d(5, "b") { day05.b.main() }
    d(6, "a") { day06.a.main() }
    d(6, "b") { day06.b.main() }
    d(7, "a") { day07.a.main() }
    d(7, "b") { day07.b.main() }
    d(8, "a") { day08.a.main() }
    d(8, "b") { day08.b.main() }
    d(9, "a") { day09.a.main() }
    d(9, "b") { day09.b.main() }
    d(10, "a") { day10.a.main() }
    d(10, "b") { day10.b.main() }
    d(11, "a") { day11.a.main() }
    d(11, "b") { day11.b.main() }
    d(12, "a") { day12.a.main() }
    d(12, "b") { day12.b.main() }
    d(13, "a") { day13.a.main() }
    d(13, "b") { day13.b.main() }
    d(14, "a") { day14.a.main() }
    d(14, "b") { day14.b.main() }
    d(15, "a") { day15.a.main() }
    d(15, "b") { day15.b.main() }
    d(16, "a") { day16.a.main() }
    d(16, "b") { day16.b.main() }
    d(17, "a") { day17.a.main() }
    d(17, "b") { day17.b.main() }
    d(18, "a") { day18.a.main() }
    d(18, "b") { day18.b.main() }
    d(19, "a") { day19.a.main() }
    d(19, "b") { day19.b.main() }
    d(20, "a") { day20.a.main() }
    d(20, "b") { day20.b.main() }
    d(21, "a") { day21.a.main() }
    d(21, "b") { day21.b.main() }
    d(22, "a") { day22.a.main() }
    d(22, "b") { day22.b.main() }
    d(23, "a") { day23.a.main() }
    d(23, "b") { day23.b.main() }
    d(24, "a") { day24.a.main() }
    d(24, "b") { day24.b.main() }
    d(25, "a") { day25.a.main() }

    fun join(a :String, b: String): String {
        val sb = StringBuilder()
        repeat(2) { sb.append(a).append(b)}
        sb.append(a)
        return sb.toString()
    }
    val head = "| Day            | Execution Time "
    val line = "|----------------|---------------:"
    println(join(head, "|     ") + "|")
    println(join(line, "|-----") + "|")
    for (i in 0 until 20) {
        for (c in 0..2) {
            val j = i + 20 * c
            if (j in results.indices) {
                val r = results[j]

                print(String.format("| [%02d.%s][src%02d%s] | %11d ms ", r.day, r.part, r.day, r.part, r.time))
            }
            if (c != 2) print("|     ")
        }
        println("|")
    }

}