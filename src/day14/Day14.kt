package day14

import println
import readInput

fun String.parseInput() =
    readInput(this).let { lines ->
        Pair(
            lines[0],
            lines.slice(2 until lines.size).associate { line -> line.split(" -> ").let { Pair(it[0], it[1]) } }
        )
    }

fun Map<String, String>.step(initial: String): String {
    var formula = initial.take(1)
    for (i in 1 until initial.length) {
        val next = initial[i]
        formula += this["${formula.last()}$next"]!! + next
    }
    return formula
}

fun part1(initial: String, rules: Map<String, String>): Int {
    val formula = (1..10).fold(initial) { acc, _ -> rules.step(acc) }
    val counts = formula.groupingBy { it }.eachCount()
    return counts.values.max() - counts.values.min()
}

fun part2(initial: String, rules: Map<String, String>): Long {
    val initialPairs = rules.mapValues { e -> initial.windowed(2).filter { it == e.key }.size.toLong() }
    val pairsAfterSteps = (1..40).fold(initialPairs) { pairs, _ ->
        pairs.entries.fold(mutableMapOf()) { transformed, it ->
            val inserted = rules[it.key]!!
            transformed.compute(it.key.first() + inserted) { _, value -> (value ?: 0) + it.value }
            transformed.compute(inserted + it.key.last()) { _, value -> (value ?: 0) + it.value }
            transformed
        }
    }
    val counts =
        pairsAfterSteps.entries.flatMap { e -> listOf(Pair(e.key.first(), e.value), Pair(e.key.last(), e.value)) }
            .groupingBy { it.first }.aggregate { _, acc: Long?, element, _ -> (acc ?: 0) + element.second }
            .mapValues { if (it.key == initial.first() || it.key == initial.last()) it.value + 1 else it.value }
            .mapValues { it.value / 2 }
    return counts.values.max() - counts.values.min()
}

fun main() {

    val testInput = "day14/input_test".parseInput()
    check(part1(testInput.first, testInput.second) == 1588)
    check(part2(testInput.first, testInput.second) == 2188189693529L)

    val input = "day14/input".parseInput()
    part1(input.first, input.second).println()
    part2(input.first, input.second).println()
}