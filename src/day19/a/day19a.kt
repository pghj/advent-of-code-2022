package day19.a

import readInputLines
import shouldBe
import util.IntVector
import vec
import java.util.regex.Pattern
import kotlin.math.max

fun main() {
    val input = read()
    var answer = 0
    input.forEach { answer += it.num * simulate(it, 24) }
    shouldBe(1266, answer)
}

fun simulate(bp: BluePrint, minutes: Int): Int {
    var best = 0
    fun addResult(outcome: Int) { best = max(best, outcome) }
    fun chooseNextRobot(bp: BluePrint, state: State) {
        if (state.minute == minutes) return addResult(state.produceOnly().stuff[3])

        // Suppose we can afford to build a geode-cracking robot in every remaining minute.
        // Would that be enough to improve on the best result so far?
        val optimisticEstimate =
                state.stuff[3] +
                (minutes-state.minute+1) * state.bots[3] +
                (minutes-state.minute) * (minutes-state.minute+1) /2
        if (optimisticEstimate < best) return

        for (i in 0..3) {
            if (canProduce(i, bp, state) && isNotAtMax(i, bp, state) ) {
                var s = state
                while(s.minute <= minutes && !canAfford(i, bp, s)) s = s.produceOnly()
                if (s.minute <= minutes) s = s.buildRobotAndProduce(i, bp)
                if (s.minute <= minutes) chooseNextRobot(bp, s)
                else addResult(s.stuff[3])
            }
        }
    }
    val start = State(
        1,
        vec(1,0,0,0), // robots
        vec(0,0,0,0), // stuff
    )
    chooseNextRobot(bp, start)
    return best
}

fun isNotAtMax(bot: Int, bp: BluePrint, state: State): Boolean {
    if (bot == 3) return true
    return bp.cost.map { it[bot] }.max() > state.bots[bot]
}

fun canAfford(i: Int, bp: BluePrint, state: State): Boolean {
    return (0..3).all {  j -> bp.cost[i][j] <= state.stuff[j] }
}

fun canProduce(i: Int, bp: BluePrint, state: State): Boolean {
    return (0..3).all {  j -> bp.cost[i][j] == 0 || state.bots[j] > 0 }
}

data class State(
    val minute: Int,
    val bots: IntVector,
    val stuff: IntVector,
) {
    fun produceOnly(): State {
        return State(
            minute+1,
            bots,
            stuff + bots,
        )
    }
    fun buildRobotAndProduce(bot: Int, bp: BluePrint): State {
        return State(
            minute+1,
            bots + IntVector.unit(4, bot),
            stuff + bots - bp.cost[bot],
        )
    }
}

data class BluePrint(
    val num: Int,
    val cost: List<IntVector>
)

fun read(): List<BluePrint> {
    return readInputLines(19)
        .filter { it.isNotBlank() }
        .map { line ->
            val a = line.split(Pattern.compile("[^0-9]+")).filter { it.isNotBlank() }.map { it.toInt() }.toList()
            val cost = ArrayList<IntVector>()
            cost.add(vec(a[1], 0, 0, 0))
            cost.add(vec(a[2], 0, 0, 0))
            cost.add(vec(a[3], a[4], 0, 0))
            cost.add(vec(a[5], 0, a[6], 0))
            BluePrint(a[0], cost)
        }
}
