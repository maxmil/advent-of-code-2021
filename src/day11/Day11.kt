package day11

import println
import readInput

data class Point(val x: Int, val y: Int)
typealias Grid = List<MutableList<Int>>

fun Grid.step(): Int {

    this.forEachIndexed { rowIndex, row -> row.forEachIndexed { colIndex, _ -> this[rowIndex][colIndex]++ } }

    val flashed = this.flatMapIndexed { rowIndex, row ->
        row.mapIndexed { colIndex, col -> if (col > 9) Point(colIndex, rowIndex) else null }
    }.filterNotNull().toMutableSet()

    val queue = ArrayDeque(flashed)
    while (queue.isNotEmpty()) {
        val octopus = queue.removeFirst()
        for (row in octopus.y - 1..octopus.y + 1) {
            if (row >= 0 && row < this.size) {
                for (col in octopus.x - 1..octopus.x + 1) {
                    if (col >= 0 && col < this[row].size && !(row == octopus.y && col == octopus.x)) {
                        this[row][col]++
                        val point = Point(col, row)
                        if (this[row][col] > 9 && !flashed.contains(point)) {
                            flashed.add(point)
                            queue.add(point)
                        }
                    }
                }
            }
        }
    }

    flashed.forEach { this[it.y][it.x] = 0 }

    return flashed.size
}

fun part1(input: List<String>): Int {
    val grid = input.map { line -> line.toCharArray().map { char -> char.digitToInt() }.toMutableList() }
    return (1..100).sumOf { grid.step() }
}

fun part2(input: List<String>): Int {
    val grid = input.map { line -> line.toCharArray().map { char -> char.digitToInt() }.toMutableList() }
    var cnt = 0
    while (grid.step() < grid.size * grid[0].size) cnt++
    return cnt + 1
}


fun main() {

    val testInput = readInput("day11/input_test")
    check(part1(testInput.toMutableList()) == 1656)
    check(part2(testInput.toMutableList()).apply { println(this) } == 195)

    val input = readInput("day11/input")
    part1(input.toMutableList()).println()
    part2(input.toMutableList()).println()
}


