package day03

import println
import readInput

fun main() {

    fun bitCounts(input: List<String>) = input
        .asSequence()
        .map { line -> line.map { it.toString().toInt() } }
        .reduce { acc, it -> acc.zip(it) { a, b -> a + b } }

    fun getRatingByCounting(input: List<String>, mostCommon: Boolean): Int = bitCounts(input)
        .map { if (it > input.size / 2) 1 else 0 }
        .map { if (mostCommon) it else 1 - it }
        .joinToString("")
        .toInt(2)

    fun getRatingByFiltering(input: List<String>, mostCommon: Boolean): Int {
        var lines = input
        var bit = 0;
        var match = ""
        while (lines.size > 1) {
            val next = if (bitCounts(lines)[bit] >= lines.size / 2.toDouble()) 1 else 0
            match += if (mostCommon) next else 1 - next
            lines = lines.filter { it.startsWith(match) }
            bit++
        }
        return lines[0].toInt(2)
    }

    fun part1(input: List<String>): Int {
        return getRatingByCounting(input, true) * getRatingByCounting(input, false)
    }

    fun part2(input: List<String>): Int {
        return getRatingByFiltering(input, true) * getRatingByFiltering(input, false)
    }

    val testInput = readInput("day03/Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("day03/Day03")
    part1(input).println()
    part2(input).println()
}
