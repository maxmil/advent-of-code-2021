package day12

import println
import readInput

fun List<String>.parse() =
    flatMap { line -> line.split("-").let { listOf(Pair(it[0], it[1]), Pair(it[1], it[0])) } }
        .groupBy { it.first }
        .mapValues { entry -> entry.value.map { it.second }.toSet() }

fun countPaths(input: List<String>, allowOneCaveTwice:Boolean = false): Int {
    val cave = input.parse()
    val complete = mutableListOf<List<String>>()
    val incomplete = cave["start"]!!.map { listOf("start", it) }.toMutableList()
    while (incomplete.isNotEmpty()) {
        val path = incomplete.removeLast()
        val next = cave[path.last()]?.filter { path.canMoveToCave(it, allowOneCaveTwice) } ?: emptyList()
        next.forEach { room ->
            val newPath = path.toMutableList().apply { add(room) }
            if (room == "end") complete.add(newPath) else incomplete.add(newPath)
        }
    }
    return complete.size
}

fun List<String>.canMoveToCave(dest: String, allowOneCaveTwice: Boolean = false) =
    dest != "start" && (dest == "end" || dest == dest.uppercase() || !contains(dest)
            || (allowOneCaveTwice && filter { it.lowercase() == it }.groupBy { it }.maxOf { it.value.size} == 1))

fun part1(input: List<String>) = countPaths(input)

fun part2(input: List<String>) = countPaths(input, true)


fun main() {

    val testInput = readInput("day12/input_test")
    check(part1(testInput) == 226)
    check(part2(testInput) == 3509)

    val input = readInput("day12/input")
    part1(input).println()
    part2(input).println()
}