package day10

import println
import readInput

val brackets = mapOf(Pair('(', ')'), Pair('[', ']'), Pair('{', '}'), Pair('<', '>'))
val scores: Map<Char, Int> = mapOf(Pair(')', 3), Pair(']', 57), Pair('}', 1197), Pair('>', 25137))
val autoCompleteScore: Map<Char, Int> = scores.mapValues { scores.keys.indexOf(it.key) + 1 }

fun part1(input: List<String>) = input.sumOf { line ->
    val stack = ArrayDeque<Char>(listOf())
    for (c in line) {
        if (stack.isNotEmpty() && brackets[stack.last()] == c) stack.removeLast()
        else if (brackets.values.contains(c)) return@sumOf scores[c]!!
        else stack.add(c)
    }
    0
}

fun part2(input: List<String>) = input.map<String, Long> { line ->
    val stack = ArrayDeque<Char>(listOf())
    for (c in line) {
        if (stack.isNotEmpty() && brackets[stack.last()] == c) stack.removeLast()
        else if (brackets.values.contains(c)) return@map 0L
        else stack.add(c)
    }
    stack.foldRight(0) { c, acc -> (acc * 5L) + autoCompleteScore[brackets[c]]!! }
}.filter { it != 0L }.sorted().let { it[(it.size - 1) / 2] }

fun main() {

    val testInput = readInput("day10/input_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("day10/input")
    part1(input).println()
    part2(input).println()
}


