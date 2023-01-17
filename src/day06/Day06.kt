package day06

import println
import readInputAsText

fun main() {

    fun parse(input: String) = input.split(",").groupingBy { it.toInt() }.eachCount().mapValues { it.value.toLong() }

    fun simulate(initial: Map<Int, Long>, days: Int): Map<Int, Long> {
        val fish = initial.toMutableMap()
        repeat(days) {
            val reproducing = fish.getOrDefault(0, 0)
            for (timer in 0..7) fish[timer] = fish.getOrDefault(timer + 1, 0)
            fish[8] = reproducing
            fish[6] = fish.getOrDefault(6, 0) + reproducing
        }
        return fish
    }

    fun part1(input: String) = simulate(parse(input), 80).values.sum()
    fun part2(input: String) = simulate(parse(input), 256).values.sum()

    val testInput = readInputAsText("day06/input_test")
    check(part1(testInput) == 5934L)
    check(part2(testInput) == 26984457539)

    val input = readInputAsText("day06/input")
    part1(input).println()
    part2(input).println()
}
