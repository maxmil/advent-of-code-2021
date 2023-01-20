package day09

import println
import readInput

data class Position(val x: Int, val y: Int)

fun main() {

    fun List<List<Int>>.withinBounds(it: Position) = it.x >= 0 && it.x < this[0].size && it.y >= 0 && it.y < this.size

    fun List<List<Int>>.isLowPoint(position: Position, value: Int): Boolean {
        return !listOf(
            position.copy(x = position.x - 1),
            position.copy(x = position.x + 1),
            position.copy(y = position.y - 1),
            position.copy(y = position.y + 1),
        ).filter { withinBounds(it) }.any { this[it.y][it.x] <= value }
    }

    fun part1(input: List<String>): Int {
        return input.map { row -> row.map { it.toString().toInt() } }
            .flatMapIndexed { rowIndex, row ->
                row.filterIndexed { colIndex, col ->
                    input.map { row -> row.map { it.toString().toInt() } }.isLowPoint(Position(colIndex, rowIndex), col)
                }
            }.sumOf { it + 1 }
    }


    fun part2(input: List<String>) = 0

    val testInput = readInput("day09/input_test")
    check(part1(testInput).apply { println(this) } == 15)
//    check(part2(testInput) == 61229)

    val input = readInput("day09/input")
    part1(input).println()
//    part2(input).println()
}


