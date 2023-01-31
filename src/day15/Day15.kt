package day15

import println
import readInput
import kotlin.math.min

data class Node(val x: Int, val y: Int, val risk: Int, var shortestPath: Int)

fun part1(input: List<String>): Int {
    val maxY = input.size - 1
    val maxX = input[0].length - 1
    val unvisited = input.flatMapIndexed { y, row ->
        row.mapIndexed { x, risk ->
            Node(x, y, risk.toString().toInt(), if (x == 0 && y == 0) 0 else Int.MAX_VALUE)
        }
    }.toMutableList()
    var curr = unvisited.sortedBy { it.shortestPath }.toMutableList().removeFirst()
    while (curr.x != maxX || curr.y != maxY) {
        val neighbourPositions = arrayListOf(Pair(-1, 0), Pair(1, 0), Pair(0, -1), Pair(0, 1))
            .map { Pair(it.first + curr.x, it.second + curr.y) }
        val neighbours = unvisited.filter { neighbourPositions.contains(Pair(it.x, it.y)) }
        neighbours.forEach { it.shortestPath = min(it.shortestPath, curr.shortestPath + it.risk) }
        unvisited.remove(curr)
        curr = unvisited.sortedBy { it.shortestPath }.toMutableList().removeFirst()
    }
    return curr.shortestPath
}

fun part2(input: List<String>): Int {
    return 0
}


fun main() {
    val testInput = readInput("day15/input_test")
    check(part1(testInput) == 40)
//    check(part2(testInput) == 315)

    val input = readInput("day15/input")
    part1(input).println()
//    part2(input).println()
}
