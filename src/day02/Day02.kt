package day02

import println
import readInput

data class Position(val aim: Int = 0, val depth: Int = 0, val position: Int = 0)

fun main() {

    fun part1(input: List<String>): Int = input.map { line -> line.split(" ") }
        .fold(Position()) { acc, next ->
            when (next[0]) {
                "forward" -> acc.copy(position = acc.position + next[1].toInt())
                "down" -> acc.copy(depth = acc.depth + next[1].toInt())
                "up" -> acc.copy(depth = acc.depth - next[1].toInt())
                else -> throw IllegalArgumentException("Don't understand ${next[0]}")
            }
        }.let { it.depth * it.position }

    fun part2(input: List<String>): Int = input.map { line -> line.split(" ") }
        .fold(Position()) { acc, next ->
            when (next[0]) {
                "forward" -> acc.copy(
                    depth = acc.depth + acc.aim * next[1].toInt(),
                    position = acc.position + next[1].toInt()
                )
                "down" -> acc.copy(aim = acc.aim + next[1].toInt())
                "up" -> acc.copy(aim = acc.aim - next[1].toInt())
                else -> throw IllegalArgumentException("Don't understand ${next[0]}")
            }
        }.let { it.depth * it.position }


    val testInput = readInput("day02/Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("day02/Day02")
    part1(input).println()
    part2(input).println()
}
