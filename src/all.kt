fun main() {

    class Result(
        val name: String,
        val time: Long
    )

    val results = ArrayList<Result>()

    fun d(s: String, job: ()->Unit) {
        println("\nDay $s\n========\n")
        val t0 = System.currentTimeMillis()
        job()
        val d = System.currentTimeMillis() - t0
        println("\ntime: ${d}ms\n")
        results.add(Result(s, d))
    }
    
    d("01.a") { day01.a.main() }
    d("01.b") { day01.b.main() }
    d("02.a") { day02.a.main() }
    d("02.b") { day02.b.main() }
    d("03.a") { day03.a.main() }
    d("03.b") { day03.b.main() }
    d("04.a") { day04.a.main() }
    d("04.b") { day04.b.main() }
    d("05.a") { day05.a.main() }
    d("05.b") { day05.b.main() }
    d("06.a") { day06.a.main() }
    d("06.b") { day06.b.main() }
    d("07.a") { day07.a.main() }
    d("07.b") { day07.b.main() }
    d("08.a") { day08.a.main() }
    d("08.b") { day08.b.main() }
    d("09.a") { day09.a.main() }
    d("09.b") { day09.b.main() }
    d("10.a") { day10.a.main() }
    d("10.b") { day10.b.main() }
    d("11.a") { day11.a.main() }
    d("11.b") { day11.b.main() }
    d("12.a") { day12.a.main() }
    d("12.b") { day12.b.main() }
    d("13.a") { day13.a.main() }
    d("13.b") { day13.b.main() }
    d("14.a") { day14.a.main() }
    d("14.b") { day14.b.main() }
    d("15.a") { day15.a.main() }
    d("15.b") { day15.b.main() }
    d("16.a") { day16.a.main() }
    d("16.b") { day16.b.main() }
    d("17.a") { day17.a.main() }
    d("17.b") { day17.b.main() }
    d("18.a") { day18.a.main() }
    d("18.b") { day18.b.main() }
    d("19.a") { day19.a.main() }
    d("19.b") { day19.b.main() }
    d("20.a") { day20.a.main() }
    d("20.b") { day20.b.main() }
    d("21.a") { day21.a.main() }
    d("21.b") { day21.b.main() }
    d("22.a") { day22.a.main() }
    d("22.b") { day22.b.main() }
    d("23.a") { day23.a.main() }
    d("23.b") { day23.b.main() }
//    d("24.a") { day24.a.main() }
//    d("24.b") { day24.b.main() }
//    d("25.a") { day25.a.main() }
//    d("25.b") { day25.b.main() }

    println("| Day  | Execution time |")
    println("|------|---------------:|")
    for (r in results) {
        println(String.format("| %s | %11d ms |", r.name, r.time))
    }

}