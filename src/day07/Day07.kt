package day07

import println
import readInputAsText
import kotlin.math.abs
import kotlin.math.min

fun main() {

    fun shortest(input: String, fuelUsed: (Int, Int) -> Int): Int {
        val start = input.split(",").map { it.toInt() }
        return (start.min()..start.max()).fold(Int.MAX_VALUE) { acc, position ->
            min(acc, start.sumOf { fuelUsed(it, position) })
        }
    }

    fun part1(input: String) = shortest(input) { start, end -> abs(start - end) }
    fun part2(input: String) = shortest(input) { start, end -> abs(start - end).let { it * (it + 1) / 2 } }

    val testInput = readInputAsText("day07/input_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInputAsText("day07/input")
    part1(input).println()
    part2(input).println()
}
